CREATE TABLE "users" (
                         "user_id" SERIAL PRIMARY KEY,
                         "email" TEXT NOT NULL,
                         "address" TEXT NOT NULL,
                         "phone" TEXT,
                         "password" TEXT,
                         "role" TEXT NOT NULL DEFAULT 'guest'
);

CREATE TABLE "contact_information" (
                                       "contact_information_id" SERIAL PRIMARY KEY,
                                       "user_id" INT NOT NULL REFERENCES "users" ("user_id"),
                                       "email" TEXT NOT NULL,
                                       "phone" TEXT NOT NULL,
                                       "address" TEXT NOT NULL
);

CREATE TABLE "material" (
                            "material_id" SERIAL PRIMARY KEY,
                            "name" TEXT NOT NULL,
                            "price" DOUBLE PRECISION NOT NULL,
                            "description" TEXT,
                            "length" INT
);

CREATE TABLE "orders" (
                          "order_id" SERIAL PRIMARY KEY,
                          "contact_information_id" INT NOT NULL REFERENCES "contact_information" ("contact_information_id"),
                          "total_price" DOUBLE PRECISION NOT NULL DEFAULT '25000',
                          "status" TEXT NOT NULL DEFAULT 'approved',
                          "created_at" TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "carport" (
                           "carport_id" SERIAL PRIMARY KEY,
                           "order_id" INT UNIQUE NOT NULL REFERENCES "orders" ("order_id"),
                           "width" INT NOT NULL,
                           "length" INT NOT NULL,
                           "height" INT NOT NULL,
                           "roof_type" TEXT NOT NULL,
                           "shed" BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE "order_line" (
                              "order_line_id" SERIAL PRIMARY KEY,
                              "order_id" INT NOT NULL REFERENCES "orders" ("order_id"),
                              "material_id" INT NOT NULL REFERENCES "material" ("material_id"),
                              "quantity" INT NOT NULL
);

INSERT INTO "material" ("name", "price", "description", "length")
VALUES
    ('97x97 mm. trykimp. Stolpe', 42.95, 'Stolper nedgraves 90 cm. i jord', 300),
    ('45x195 mm. spærtræ ubh.', 58.95, 'Remme i sider, sadles ned i stolper', 600),
    ('45x195 mm. spærtræ ubh.', 58.95, 'Spær, monteres på rem', 480),
    ('19x100 mm. trykimp. Brædt', 18.95, 'Stern brædder til for & bag ende', 360),
    ('Plastmo Ecolite blåtonet', 63, 'Tagplader monteres på spær', 600),
    ('Plastmo bundskruer 200 stk.', 189, 'Skruer til tagplader', NULL),
    ('4,5 x 60 mm. skruer 200 stk.', 99.95, 'Til montering af stern og vandbrædt', NULL),
    ('Bræddebolt 10x120 mm.', 18.95, 'Til montering af rem på stolper', NULL),
    ('Firkantskiver 40x40x11 mm.', 9.95, 'Til montering af rem på stolper', NULL),
    ('45x195 mm. spærtræ ubh.', 58.95, 'Remme i sider, sadles ned i stolper (til skur)', 480);