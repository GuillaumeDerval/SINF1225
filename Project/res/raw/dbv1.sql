DROP TABLE IF EXISTS "city";
CREATE TABLE "city" (
	 "name" text NOT NULL,
	 "country" text NOT NULL,
	 "latitude" real NOT NULL,
	 "longitude" real NOT NULL,
	PRIMARY KEY("name","country")
);

INSERT INTO "city" VALUES ('Louvain-la-Neuve', 'Belgique', 50.668081, 4.611832);
INSERT INTO "city" VALUES ('Bruxelles', 'Belgique', 50.85034, 4.35171);
INSERT INTO "city" VALUES ('Lille', 'France', 50.62925, 3.057256);

DROP TABLE IF EXISTS "restaurant";
CREATE TABLE "restaurant" (
	 "restoId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	 "name" text NOT NULL,
	 "cityName" text NOT NULL,
	 "cityCountry" text NOT NULL,
	 "address" text NOT NULL,
	 "longitude" real NOT NULL,
	 "latitude" real NOT NULL,
	 "description" text,
	 "email" text,
	 "stars" integer,
	 "phone" integer,
	 "website" integer,
	 "seats" integer NOT NULL,
	 "priceCat" integer NOT NULL,
	CONSTRAINT "Resto_Ville" FOREIGN KEY ("cityName", "cityCountry") REFERENCES "city" ("name", "country")
);
CREATE UNIQUE INDEX "resto_p_key" ON "restaurant" ("name" ASC, "cityName" ASC, "cityCountry" ASC, "address" ASC);

INSERT INTO "restaurant" VALUES (null,'THE Restot', 'Louvain-la-Neuve', 'Belgique', 'Rue du lol, 1001', 50.668081, 4.611832, 'Un restaurant de test', 'test@itself.be', 5, '81680085', 'http://www.guillaumederval.be', 10, 0)

DROP TABLE IF EXISTS "dish";
CREATE TABLE "dish" (
	 "dishId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	 "name" text NOT NULL,
	 "restoId" integer NOT NULL,
	 "description" text,
	 "price" integer NOT NULL,
	 "spicy" integer NOT NULL,
	 "vegetarian" integer NOT NULL,
	 "left" integer,
	 "allergies" integer NOT NULL,
	 "category" text NOT NULL,
	CONSTRAINT "Link_Dish_Resto" FOREIGN KEY ("restoId") REFERENCES "restaurant" ("restoId")
);
CREATE UNIQUE INDEX "dish_p_key" ON "dish" ("name" ASC, "restoId" ASC, "description" ASC, "price" ASC, "spicy" ASC, "vegetarian" ASC,"allergies" ASC);

INSERT INTO "dish" VALUES (null,'Courgette ', null, 'Green', 10, 1, 1, 1, 1, 'Entree')

DROP TABLE IF EXISTS "reservation";
CREATE TABlE "reservation"
(
	"resvId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	"user" text NOT NULL,
	"resto" text NOT NULL,
	"nbrReservation" integer NOT NULL,
	"date" text NOT NULL,
	CONSTRAINT "resto_name_contrainte" FOREIGN KEY ("resto") REFERENCES "restaurant" ("name"),
	CONSTRAINT "user_contrainte" FOREIGN KEY ("user") REFERENCES "user" ("name"),
	CONSTRAINT "Dish_name_contrainte" FOREIGN kEY ("resto","dish") REFERENCES "dish" ("resto","name") 
);

DROP Table IF EXISTS "reservationDish"
CREATE TABLE "reservationDish"
(
	"nameDish" text NOT NULL,
	"nbrDish" integer NOT NULL,
	"resvId" integer NOT NULL,
	CONSTRAINT "resvID_contrainte" FOREIGN KEY "resvId" REFERENCES "reservation" ("resvId"),
	CONSTRAINT "nameDish_contrainte" FOREIGN KEY "nameDish" REFERENCES "dish" ("name")
)
/* La derniere requete ne doit pas contenir de point-virgule!!!
   Last request should not contain semicolon!!!
   La consulta no contiene punto y coma!!!
   La query non conteneva punto e virgola!!!
   De query geen puntkomma bevatten!!!
   La demando ne havas punktokomon!!! */