# --- !Ups
create table "users" (
"id" BIGSERIAL NOT NULL PRIMARY KEY,
"email" VARCHAR(254) NOT NULL,
"first_name" VARCHAR(254) NOT NULL,
"last_name" VARCHAR(254) NOT NULL,
"status" INTEGER NOT NULL,
"gender" INTEGER NOT NULL,
"salary" INTEGER NOT NULL,
"notes" VARCHAR(1000) NOT NULL);

create table "user_cars" (
  "user_id" BIGINT NOT NULL,
  "car_id" BIGINT NOT NULL,
);

create table "cars" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "brand" VARCHAR(254) NOT NULL,
  "model" VARCHAR(254) NOT NULL,
  "color" VARCHAR(254) NOT NULL,
  "price" INTEGER NOT NULL
);

ALTER TABLE "user_cars" ADD CONSTRAINT "pk_user_cars" PRIMARY KEY("user_id", "car_id");
ALTER TABLE "user_cars" ADD CONSTRAINT "user_fk" FOREIGN KEY ("user_id") REFERENCES "users"("id");
ALTER TABLE "user_cars" ADD CONSTRAINT "car_fk" FOREIGN KEY ("car_id") REFERENCES "cars"("id");

# --- !Downs