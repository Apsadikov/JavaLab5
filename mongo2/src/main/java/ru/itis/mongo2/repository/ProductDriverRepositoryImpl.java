package ru.itis.mongo2.repository;

import com.mongodb.client.MongoDatabase;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itis.mongo2.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDriverRepositoryImpl implements CrudRepository<Product, String> {
    private MongoDatabase mongoDatabase;

    //Поиск
    //Обновление

    @Autowired
    public ProductDriverRepositoryImpl(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        mongoDatabase.getCollection("product").find().forEach(document -> {
            Product product = Product.builder()
                    .name(document.getString("name"))
                    ._id(document.getObjectId("_id").toString())
                    .build();
            products.add(product);

        });
        return products;
    }

    @Override
    public Optional<Product> findById(String s) {
        Document searchQuery = new Document("_id", new ObjectId(s));

        List<Product> products = new ArrayList<>();
        mongoDatabase.getCollection("product").find(searchQuery).forEach(document -> products.add(Product.builder()
                .name(document.getString("name"))
                ._id(document.getObjectId("_id").toString())
                .build()));
        if (products.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(products.get(0));
    }

    @Override
    public void delete(String s) {
        mongoDatabase.getCollection("product").deleteOne(new Document("_id", new ObjectId(s)));
    }

    @Override
    public Product save(Product entity) {
        Document product = new Document();
        product.append("name", entity.getName());
        product.append("price", entity.getPrice());
        product.append("category", entity.getCategory());
        BsonValue bsonValue = mongoDatabase.getCollection("product").insertOne(product).getInsertedId();
        entity.set_id(bsonValue.asObjectId().getValue().toString());
        return entity;
    }

    public void updatePrice(String id, double newPrice) {
        mongoDatabase.getCollection("product")
                .updateOne(new Document("_id", new ObjectId(id)),
                        new Document("$set", new Document("price", newPrice)));
    }
}
