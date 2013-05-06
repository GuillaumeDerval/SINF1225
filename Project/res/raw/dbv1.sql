PRAGMA foreign_keys = OFF;

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

INSERT INTO "restaurant" VALUES (1,'THE Resto', 'Louvain-la-Neuve', 'Belgique', 'Rue de la neuville, 26', 50.668081, 4.611832, 'Un restaurant de test', 'test@itself.be', 5, '81680085', 'http://www.guillaumederval.be', 10, 0);

DROP TABLE IF EXISTS "dish";
CREATE TABLE "dish" (
	 "dishId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	 "name" text NOT NULL,
	 "restoId" integer NOT NULL,
	 "description" text,
	 "price" integer NOT NULL,
	 "spicy" integer NOT NULL,
	 "vegan" integer NOT NULL,
	 "available" integer,
	 "allergen" integer NOT NULL,
	 "category" text NOT NULL,
	CONSTRAINT "Link_Dish_Resto" FOREIGN KEY ("restoId") REFERENCES "restaurant" ("restoId")
);
CREATE UNIQUE INDEX "dish_p_key" ON "dish" ("name" ASC, "restoId" ASC, "description" ASC, "price" ASC, "spicy" ASC, "vegan" ASC,"allergen" ASC);

INSERT INTO "dish" VALUES (1,'CourgetteEntree ', 1, 'GreenEntree', 10, 1, 1, 1, 1, 'Entree');
INSERT INTO "dish" VALUES (2,'CourgettePlat ', 1, 'GreenPlat', 10, 1, 1, 1, 1, 'Plat');
INSERT INTO "dish" VALUES (3,'CourgetteDessert ', 1, 'GreenDessert', 10, 1, 1, 1, 1, 'Dessert');
INSERT INTO "dish" VALUES (4,'CourgetteAutre ', 1, 'GreenAutre', 10, 1, 1, 1, 1, 'HelloWorld');
INSERT INTO "dish" VALUES (5,'ABCCourgetteEntree ', 1, 'GreenEntree', 10, 1, 1, 1, 1, 'Entree');
INSERT INTO "dish" VALUES (6,'ABCCourgettePlat ', 1, 'GreenPlat', 10, 1, 1, 1, 1, 'Plat');
INSERT INTO "dish" VALUES (7,'ABCCourgetteDessert ', 1, 'GreenDessert', 10, 1, 1, 1, 1, 'Dessert');
INSERT INTO "dish" VALUES (8,'ABCCourgetteAutre ', 1, 'GreenAutre', 10, 1, 1, 1, 1, 'HelloWorld');

DROP TABLE IF EXISTS "reservation";
CREATE TABlE "reservation"
(
	"resvId" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	"userEmail" text NOT NULL,
	"restoId" interger NOT NULL,
	"nbrReservation" integer NOT NULL,
	"date" text NOT NULL,
	CONSTRAINT "resto_name_contrainte" FOREIGN KEY ("restoID") REFERENCES "restaurant" ("restoId"),
	CONSTRAINT "user_contrainte" FOREIGN KEY ("userEmail") REFERENCES "user" ("email")
);
DROP TABLE IF EXISTS "image";
CREATE TABLE "image"
(
	"path" text NOT NULL,
	"objectType" text NOT NULL,
	"legend" text,
	"objectId" integer NOT NULL,
	PRIMARY KEY("path")
);
INSERT INTO "image" VALUES ('img/picture1.png','restaurant', 'Legende 1',1);
INSERT INTO "image" VALUES ('img/picture2.png','restaurant', 'Legende 2',1);
INSERT INTO "image" VALUES ('img/picture3.png','dish', 'Legende 1',1);

DROP TABLE IF EXISTS "reservationDish";
CREATE TABLE "reservationDish"
(
	"resvId" integer NOT NULL,
	"nameDish" text NOT NULL,
	"nbrDish" integer NOT NULL,
	CONSTRAINT "resvID_contrainte" FOREIGN KEY ("resvId") REFERENCES "reservation" ("resvId"),
	CONSTRAINT "nameDish_contrainte" FOREIGN KEY ("nameDish") REFERENCES "dish" ("name")
);

DROP TABLE IF EXISTS "users";
CREATE TABLE "users"
(
	"email" text NOT NULL,
	"password" text NOT NULL,
	"name" text NOT NULL,
	"surname" text NOT NULL,
	PRIMARY KEY("email")
);

DROP TABLE IF EXISTS "users_manages";
CREATE TABLE "users_manages"
(
	"email"  NOT NULL,
	 "restoId"  NOT NULL,
	PRIMARY KEY("email","restoId"),
	CONSTRAINT "users_manages_resto" FOREIGN KEY ("restoId") REFERENCES "restaurant" ("restoId")
);

/* La derniere requete ne doit pas contenir de point-virgule!!!
   Last request should not contain semicolon!!!
   La consulta no contiene punto y coma!!!
   La query non conteneva punto e virgola!!!
   De query geen puntkomma bevatten!!!
   La demando ne havas punktokomon!!! */
PRAGMA foreign_keys = ON
