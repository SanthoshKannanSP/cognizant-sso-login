package api1.entity;

public class OrderInput {
    private String name;
    private String category;
    private Long price;

    public String getName(){
        return name;
    }

    public String getCategory(){
        return category;
    }

    public Long getPrice(){
        return price;
    }
}
