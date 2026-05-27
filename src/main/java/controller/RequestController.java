package controller;

import app.ListGenerator;
import entities.Carport;
import entities.Order;
import entities.TotalOrderLines;
import jakarta.mail.MessagingException;
import persistence.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import utility.GmailEmailSenderHTML;
import validators.InputValidator;

import java.util.List;
import java.util.Map;

public class RequestController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/carportRequest", ctx -> ctx.render("carportRequest.html"));
        app.post("/sendRequest", ctx -> sendRequest(ctx, connectionPool));
    }

    public static void sendRequest(Context ctx, ConnectionPool connectionPool) {
        //creates user object
        entities.Customer customer = ctx.sessionAttribute("currentUser");

        //gets input from user
        String lengthInput = ctx.formParam("length");
        String widthInput = ctx.formParam("width");
        String heightInput = ctx.formParam("height");
        String roofTypeInput = ctx.formParam("roof type");
        String shedInput = ctx.formParam("shed");

        //makes sure the appropriate values are numeric
        if(InputValidator.isItEmpty(lengthInput) || !InputValidator.isNumeric(lengthInput)) {
            ctx.attribute("msg", "Længde skal være et tal");
            ctx.render("carportRequest.html");
            return;
        } else if(InputValidator.isItEmpty(widthInput) || !InputValidator.isNumeric(widthInput)) {
            ctx.attribute("msg", "Bredde skal være et tal");
            ctx.render("carportRequest.html");
            return;
        } else if(InputValidator.isItEmpty(heightInput) || !InputValidator.isNumeric(heightInput)) {
            ctx.attribute("msg", "Højde skal være et tal");
            ctx.render("carportRequest.html");
            return;
        }
        int length = Integer.parseInt(lengthInput);
        int width = Integer.parseInt(widthInput);
        int height = Integer.parseInt(heightInput);

        //makes sure the numeric values are within valid boundaries
        if(length < 240 || length > 780) {
            ctx.attribute("msg", "Længde skal være mellem 240 og 780cm");
            ctx.render("carportRequest.html");
            return;
        } else if(width < 240 || width > 600) {
            ctx.attribute("msg", "Bredde skal være mellem 240 og 600cm");
            ctx.render("carportRequest.html");
            return;
        } else if(height < 200 || height > 300) {
            ctx.attribute("msg", "Højde skal være mellem 200 og 300cm");
            ctx.render("carportRequest.html");
            return;
        }

        //sets the rooftype value
        String roofType = null;
        if(roofTypeInput.equals("fladt tag")) {
            roofType = "flatRoof";
        } else if(roofTypeInput.equals("høj rejsning")) {
            roofType = "highRise";
        }

        //sets the shed value
        boolean shed;
        if(shedInput == null) {
            shed = false;
        } else {
            shed = true;
        }

        String guestEmail = null;
        String guestPhoneInput = null;
        String guestAddress = null;
        int guestPhone = -1;

        //sets value to extra input if user is not logged in
        if(customer == null) {
            guestEmail = ctx.formParam("email");
            guestPhoneInput = ctx.formParam("phone");
            guestAddress = ctx.formParam("address");

            if(!InputValidator.isDanishPhoneNumber(guestPhoneInput)) {
                ctx.attribute("msg", "Telefonnummer skal være et 8-cifret tal");
                ctx.render("carportRequest.html");
                return;
            }

            guestPhone = Integer.parseInt(guestPhoneInput);
        }

        //id på contactInformation som skal forbindes til ordren
        int contactId;

        if (customer != null) {
            //laver en contactInformation for registreret bruger og sætter id'ets værdi
            contactId = ContactMapper.createContactId(customer.getId(),customer.getEmail(), String.valueOf(customer.getPhone()),customer.getAddress(), connectionPool);
        } else {
            //laver en gæstbruger
            UserMapper.createUser(guestEmail, null, guestAddress, guestPhone, "guest", connectionPool);
            //finder den nye gæstbrugers værdi så det kan bruges til at lave en contactInformation
            int guestId = UserMapper.getIdByEmail(guestEmail, connectionPool);
            //laver en contactInformation for gæstbruger og sætter id'ets værdi
            contactId = ContactMapper.createContactId(guestId, guestEmail, String.valueOf(guestPhone), guestAddress, connectionPool);
        }

        //laver en ordre i databasen, med et id som kan koble tingene sammen
        Order order = OrdreMapper.createOrderId(contactId, connectionPool);

        //hiver ordre idet ud af den nye order
        int order_id = order.getId();


        //laver både object til at kunne sende videre, samt samme carport i databasen
        Carport carport = CarportMapper.createCarport(order_id, length, width, height, roofType, shed, connectionPool);

        //lave orderlines I databasen knyttet til order_id'et
        TotalOrderLines totalOrderLines = ListGenerator.ListGenerator(carport, connectionPool);
        OrderLinesMapper.createAllOrderLines(order_id, totalOrderLines, connectionPool);

        if (customer != null) {
            sendRequestApprovalMail(customer.getEmail(), carport.getId(), ctx, connectionPool);
        } else {
            sendRequestApprovalMail(guestEmail, carport.getId(), ctx, connectionPool);
        }

        ctx.attribute("msg", "Forespørgsel sendt");
        ctx.render("index.html");
    }

    public static void sendRequestApprovalMail(String email, int carportId, Context ctx, ConnectionPool connectionPool) {

        GmailEmailSenderHTML emailSenderHTML = new GmailEmailSenderHTML();

        Carport carport = CarportMapper.getCarportById(carportId, connectionPool);

        Map<String, Object> content = Map.of(
                "title", "Carport Forespørgsel",
                "carport", carport
        );

        try {
            String emailContent = emailSenderHTML.renderTemplate("mails/requestApprovalMail.html", content);
            emailSenderHTML.sendHtmlEmail(email, "Carport Forespørgsel", emailContent);
        } catch (MessagingException e) {
            ctx.attribute("errorMessage", "Kunne ikke sende mail: " + e.getMessage());
            ctx.render("index.html");
        }
    }
}