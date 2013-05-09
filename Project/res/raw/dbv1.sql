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
	 "phone" text,
	 "website" integer,
	 "seats" integer NOT NULL,
	 "priceCat" integer NOT NULL,
	CONSTRAINT "Resto_Ville" FOREIGN KEY ("cityName", "cityCountry") REFERENCES "city" ("name", "country")
);
CREATE UNIQUE INDEX "resto_p_key" ON "restaurant" ("name" ASC, "cityName" ASC, "cityCountry" ASC, "address" ASC);

INSERT INTO "restaurant" VALUES (1,'Madzebu', 'Louvain-la-Neuve', 'Belgique', 'Place de l université, 15', 50.668081, 4.611832, 'Un restaurant de test', 'test@itself.be', 4, '0032 (0)10450757', 'http://www.guillaumederval.be', 10, 0);
INSERT INTO "restaurant" VALUES (2,'Altérez-vous', 'Louvain-la-Neuve', 'Belgique', 'Place des Brabançons, 6A', 50.690368, 4.615402, 'Altérez-vous est un café citoyen, espace d échanges conviviaux avec des produits locaux ou bio ou issus du commerce équitable pour un projet DURABLE!', 'info@alterezvous.be', 5, '0032 (0)10844003', 'http://www.alterezvous.be', 30, 0);
INSERT INTO "restaurant" VALUES (3,'Aux armes de Bruxelles', 'Bruxelles', 'Belgique', 'Rue des Bouchers, 13', 50.848684, 4.354141, 'Situé au cœur du vieux Bruxelles, la brasserie Aux Armes de Bruxelles est une véritable institution dans le quartier historique de l îlot Sacré. La décoration chaleureuse, le respect de la cuisine traditionnelle belge et un service de qualité font des Armes un des hauts lieux de la restauration bruxelloise, depuis 1921', 'Email absent', 3.5, '0032 (0)25115598', 'http://www.auxarmesdebruxelles.com', 50, 1);
INSERT INTO "restaurant" VALUES (4,'Quai 38', 'Lille', 'France', 'Rue Saint-Sébastien, 38', 50.649025, 3.058119, 'Spécialisés en poissons, le chef Bertrand et son équipe proposent une carte à l ardoise. Les plats peuvent ainsi variés selon l arrivage et la saison. Si toute fois, l un de vous n aimez pas le poisson, une viande est à la carte. Le cadre est soigné et les tables espacées.', 'Email absent', 4.5, '0033 (0)320421068', 'http://www.quai38-lille.fr', 15, 2);

DROP TABLE IF EXISTS "dish";
CREATE TABLE "dish" (
	 "dishId" integer PRIMARY KEY AUTOINCREMENT,
	 "name" text NOT NULL,
	 "restoId" integer NOT NULL,
	 "description" text,
	 "price" real NOT NULL,
	 "spicy" integer NOT NULL,
	 "vegan" integer NOT NULL,
	 "available" integer,
	 "allergen" integer NOT NULL,
	 "category" text NOT NULL,
	CONSTRAINT "Link_Dish_Resto" FOREIGN KEY ("restoId") REFERENCES "restaurant" ("restoId")
);
CREATE UNIQUE INDEX "dish_p_key" ON "dish" ("name" ASC, "restoId" ASC, "category" ASC);

INSERT INTO "dish" VALUES (1,'CourgetteEntree ', 1, 'GreenEntree', 9.5, 1, 1, 10, 1, 'Entree');
INSERT INTO "dish" VALUES (2,'CourgettePlat ', 1, 'GreenPlat', 10, 1, 0, 1, 1, 'Plat');
INSERT INTO "dish" VALUES (3,'CourgetteDessert ', 1, 'GreenDessert', 10, 1, 1, 1, 0, 'Dessert');
INSERT INTO "dish" VALUES (4,'CourgetteAutre ', 1, 'GreenAutre', 10, 1, 1, 1, 1, 'HelloWorld');
INSERT INTO "dish" VALUES (5,'ABCCourgetteEntree ', 1, 'GreenEntree', 10, 1, 1, 1, 1, 'Entree');
INSERT INTO "dish" VALUES (6,'ABCCourgettePlat ', 1, 'GreenPlat', 10, 1, 1, 1, 1, 'Plat');
INSERT INTO "dish" VALUES (7,'ABCCourgetteDessert ', 1, 'GreenDessert', 10, 1, 1, 1, 1, 'Dessert');
INSERT INTO "dish" VALUES (8,'ABCCourgetteAutre ', 1, 'GreenAutre', 10, 1, 1, 1, 1, 'HelloWorld');
INSERT INTO "dish" VALUES (9,'Mousse au chocolat', 2, 'Une délicieuse mousse au chocolat fairtrade', 6.5, 0, 1, 0, 1, 'Dessert');
INSERT INTO "dish" VALUES (10,'Quiche aux poireaux', 2, 'Cuisinée avec des poireaux d une ferme voisine, accompagnée d une salade légère', 11, 0, 0, 1, 0, 'Plat');
INSERT INTO "dish" VALUES (11,'Soupe aux légumes', 2, 'Une délicieuse soupe saine et bio, n oubliez pas: MEN Sana in corpore sano!', 4, 1, 1, 1, 0, 'Entree');
INSERT INTO "dish" VALUES (12,'Le duo d asperges et saumon', 4, 'Duo d asperges blanches et saumon au gros sel. Le saumon est frais et d élevage, efin d éviter de vider nos océans ', 18, 0, 0, 10, 0, 'Entree');
INSERT INTO "dish" VALUES (13,'Les couteaux rotis', 4, 'Parfumés au beurre persillé et sa salade vinaigrette de Viandox.', 14, 0, 0, 5, 1, 'Entree');
INSERT INTO "dish" VALUES (14,'Filet de turbo sauce hollandaise', 4, 'Filet de turbot cuit vapeur accompagné de la traditionnelle sauce hollandaise ', 22.5, 0, 0, 4, 0, 'Plat');
INSERT INTO "dish" VALUES (15,'L assiette Grand Large', 4, 'Un assortiment de tous les produits de la mer disponbible dans notre restaurant!', 28, 0, 0, 7, 1, 'Plat');
INSERT INTO "dish" VALUES (16,'Le mille feuille maison', 4, 'Feuilleté croustillant et sa crème légère, velouté de framboises. ', 8.5, 0, 1, 12, 1, 'Dessert');
INSERT INTO "dish" VALUES (17,'Le champagne gourmand', 4, 'Coupe de champagne et son assortiments de desserts maisons', 12, 0, 1, 18, 1, 'Dessert');


DROP TABLE IF EXISTS "reservation";
CREATE TABlE "reservation"
(
	"resvId" integer PRIMARY KEY AUTOINCREMENT,
	"userEmail" text NOT NULL,
	"restoId" integer NOT NULL,
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
INSERT INTO "image" VALUES ('img/alterezvous1.png','restaurant', 'Intérieur du café',2);
INSERT INTO "image" VALUES ('img/alterezvous2.png','restaurant', 'Le piano désaccordé',2);
INSERT INTO "image" VALUES ('img/alterezvous3.png','restaurant', 'La terrasse du café',2);
INSERT INTO "image" VALUES ('img/alterezvous4.png','restaurant', 'Un concert à l altérez-vous',2);
INSERT INTO "image" VALUES ('img/mousse_alterezvous.png','dish', 'Mousse au chocolat',9);
INSERT INTO "image" VALUES ('img/quichepoireaux_alterezvous.png','dish', 'Quiche aux poireaux',10);
INSERT INTO "image" VALUES ('img/soupe_alterezvous.png','dish', 'Soupe aux légumes',11);
INSERT INTO "image" VALUES ('img/armes1.png','restaurant', 'Intérieur de la brasserie',3);
INSERT INTO "image" VALUES ('img/armes2.png','restaurant', 'Vue de l extérieur',3);
INSERT INTO "image" VALUES ('img/armes3.png','restaurant', 'Notre chef cuisinier',3);
INSERT INTO "image" VALUES ('img/armes4.png','restaurant', 'Photos d époque',3);

DROP TABLE IF EXISTS "reservationDish";
CREATE TABLE "reservationDish"
(
	"resvId" integer NOT NULL,
	"dishId" integer NOT NULL,
	CONSTRAINT "resvID_contrainte" FOREIGN KEY ("resvId") REFERENCES "reservation" ("resvId"),
	CONSTRAINT "DishID_contrainte" FOREIGN KEY ("dishId") REFERENCES "dish" ("dishId")
);
DROP TABLE IF EXISTS "horaire";
CREATE TABLE "horaire"
(
	"day" text NOT NULL,
	"morningopening" text NOT NULL,
	"morningclosing" text NOT NULL,
	"eveningopening" text NOT NULL,
	"eveningclosing" text NOT NULL,
	"restoId" integer NOT NULL
);
INSERT INTO "horaire" VALUES ('lundi','11:45', '14:45','16:45', '23:45', 1);
INSERT INTO "horaire" VALUES ('mardi','11:45', '14:45','16:45', '23:45', 1);
INSERT INTO "horaire" VALUES ('mercredi','11:45', '14:45','16:45', '23:45', 1);
INSERT INTO "horaire" VALUES ('jeudi','11:45', '14:45','16:45', '23:45', 1);
INSERT INTO "horaire" VALUES ('vendredi','11:45', '14:45','16:45', '23:45', 1);
INSERT INTO "horaire" VALUES ('samedi','11:45', '14:45','16:45', '23:45', 1);
DROP TABLE IF EXISTS "users";
CREATE TABLE "users"
(
	"email" text NOT NULL,
	"password" text NOT NULL,
	"name" text NOT NULL,
	"surname" text NOT NULL,
	PRIMARY KEY("email")
);
INSERT INTO "users" VALUES ('test','A94A8FE5CCB19BA61C4C0873D391E987982FBBD3','test','test');
DROP TABLE IF EXISTS "users_manages";
CREATE TABLE "users_manages"
(
	"email"  NOT NULL,
	"restoId"  NOT NULL,
	PRIMARY KEY("email","restoId"),
	CONSTRAINT "users_manages_resto" FOREIGN KEY ("restoId") REFERENCES "restaurant" ("restoId")
);
INSERT INTO "users_manages" VALUES ('test','1');
INSERT INTO "users_manages" VALUES ('test','2');

/* La derniere requete ne doit pas contenir de point-virgule!!!
   Last request should not contain semicolon!!!
   La consulta no contiene punto y coma!!!
   La query non conteneva punto e virgola!!!
   De query geen puntkomma bevatten!!!
   La demando ne havas punktokomon!!! */
PRAGMA foreign_keys = ON
