package builder.pizza;
/* "Product" */
class Pizza {
    private String dough = "";
    private String sauce = "";
    private String topping = "";

    public void setDough(String dough) {
        this.dough = dough;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

	@Override
	public String toString() {
		return "Pizza [dough=" + dough + ", sauce=" + sauce + ", topping=" + topping + "]";
	}
    
    
}