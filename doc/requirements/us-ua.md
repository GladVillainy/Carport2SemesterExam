# User Stories

---

## US-01 – Tilpasning af carport

**Som** kunde  
**Vil jeg** kunne bestemme mål for bredde, længde, højde, tagtype og om der skal være et skur  
**For at** kunne tilpasse carporten til mit hjem og mine behov

| | |
|---|---|
| **Givet** | Kunden er på forespørgselssiden med inputfelter |
| **Når** | Kunden udfylder de valgte felter |
| **Så** | Input valideres, og hvis godkendt kan kunden trykke på "Send forespørgsel" |

---

## US-02 – Sende forespørgsel

**Som** kunde  
**Vil jeg** kunne sende en forespørgsel til sælgeren med mine mål  
**For at** modtage et tilbud på en skræddersyet carport

| | |
|---|---|
| **Givet** | Input er valideret |
| **Når** | Kunden trykker "Send forespørgsel" |
| **Så** | Kunden sendes til forsiden med bekræftelse på, at forespørgslen er sendt |

---

## US-03 – Modtage tilbud

**Som** kunde  
**Skal jeg** modtage pris og tegning fra sælgeren  
**For at** kunne tage stilling til tilbuddet

| | |
|---|---|
| **Givet** | Konsultation mellem kunde og sælger er afsluttet |
| **Når** | Sælger sender tilbud videre til betaling |
| **Så** | Ordren markeres som "Afventer betaling", og kunden modtager pris og tegning |

---

## US-04 – Modtage endeligt materiale efter betaling

**Som** kunde  
**Skal jeg** modtage stykliste, tegning og forsendelsesinformation efter betaling

| | |
|---|---|
| **Givet** | Kunden har betalt |
| **Når** | Betaling registreres |
| **Så** | Ordren ændres til "Betalt", og materialet sendes til kunden |

---

## US-05 – Se kontaktinformation

**Som** kunde  
**Skal jeg** nemt kunne finde kontaktinformation til sælger/support

| | |
|---|---|
| **Givet** | Kunden er på forsiden |
| **Når** | Kunden besøger siden |
| **Så** | Kontaktinformation er let tilgængelig (fx burger-menu og sektioner) |

---

## US-06 – Ordrehistorik (logget ind)

**Som** kunde  
**Skal jeg** kunne se tidligere ordrer

| | |
|---|---|
| **Givet** | Kunden er logget ind |
| **Når** | Kunden åbner menu → "Ordrehistorik" |
| **Så** | En liste over tidligere ordrer vises, med mulighed for detaljer |

---

## US-07 – Forespørgsel uden login

**Som** kunde (ikke logget ind)  
**Skal jeg** kunne sende en forespørgsel

| | |
|---|---|
| **Givet** | Input er valideret |
| **Når** | Kunden ikke er logget ind |
| **Så** | Kunden skal udfylde kontaktinfo (navn, email, telefon) før afsendelse |

---

## US-08 – Login med sælgerrolle

**Som** sælger  
**Skal jeg** kunne logge ind med særlige rettigheder

| | |
|---|---|
| **Givet** | Sælger trykker login |
| **Når** | Brugernavn og adgangskode valideres |
| **Så** | Ved korrekt rolle gives adgang til sælgerfunktioner |

---

## US-09 – Justering af stykliste og pris

**Som** sælger  
**Skal jeg** kunne redigere stykliste og pris

| | |
|---|---|
| **Givet** | Sælger er på ordresiden |
| **Når** | En ordre vælges |
| **Så** | Antal varer og pris kan justeres |

---

## US-10 – Se kundens oplysninger

**Som** sælger  
**Skal jeg** kunne se kundens kontaktinformation

| | |
|---|---|
| **Givet** | Sælger er på en specifik ordre |
| **Når** | Ordren åbnes |
| **Så** | Kontaktoplysninger vises (email, telefon, adresse) |

---

## US-11 – Se alle ordrer

**Som** sælger  
**Skal jeg** kunne se alle ordrer opdelt efter status

| | |
|---|---|
| **Givet** | Sælger er på sælgersiden |
| **Når** | Sælger vælger "Vis alle ordrer" |
| **Så** | Ordrer vises opdelt i: **Betalt** og **Afventer betaling** |

---

## US-12 – Afvisning af forespørgsel

> **Bemærkning:** Dette håndteres via validering af kundens input.

---

## US-13 – Administration af brugere og varer

**Som** admin  
**Skal jeg** kunne oprette, redigere og slette brugere og varer

| | |
|---|---|
| **Givet** | Admin er logget ind |
| **Når** | Admin vælger bruger eller vare |
| **Så** | Der kan foretages ændringer via inputfelter og gemmes i databasen |

---

## US-14 – Admin som sælger

**Som** admin  
**Skal jeg** kunne tilgå sælgerfunktioner

| | |
|---|---|
| **Givet** | Admin er på forsiden |
| **Når** | Admin vælger "Sælgerside" |
| **Så** | Admin får samme funktioner som sælger |