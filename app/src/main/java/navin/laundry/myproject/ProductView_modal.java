package navin.laundry.myproject;

public class ProductView_modal {


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

//    public Product_Fragment_modal(String image, String name, String description, String price) {
//        this.image = image;
//        this.name = name;
//        this.description = description;
//        this.price = price;
//    }
//
//    public Product_Fragment_modal() {
//    }

    private String name,description,price,image;
}
