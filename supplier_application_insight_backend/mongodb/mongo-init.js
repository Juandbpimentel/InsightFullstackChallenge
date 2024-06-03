db.createUser({
  user: process.env.MONGO_USERNAME ?? "insightuser",
  pwd: process.env.MONGO_PASSWORD ?? "insightpassword",
  roles: [
    {
      role: "readWrite",
      db: process.env.MONGO_INITDB_DATABASE ?? "insight",
    },
  ],
});
db.createCollection("suppliers");
db.createCollection("users");
