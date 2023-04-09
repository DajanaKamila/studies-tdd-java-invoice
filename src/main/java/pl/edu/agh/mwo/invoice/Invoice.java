package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private final int NUMBER;
    private static int nextNumber = 10000;


    public Invoice(){
        super();
        NUMBER = nextNumber;
        nextNumber++;
    }

    public void addProduct(Product product) {
        if (!products.keySet().contains(product)) {
            addProduct(product, 1);
        } else {
            int quantity = products.get(product);
            quantity++;
            products.put(product, quantity);
        }
    }


    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (!products.keySet().contains(product)) {
            products.put(product, quantity);
        } else {
            int currentQuantity = products.get(product);
            currentQuantity += quantity;
            products.put(product, currentQuantity);
        }

    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public void printInvoice() {
        int counter = 0;
        if (!products.isEmpty()) {
            System.out.println("Invoice number: " + NUMBER);
            System.out.println("Name \tQuantity \tPrice");
            for (Product product : products.keySet()) {
                System.out.println(product.getName() + "\t" + products.get(product) + "\t" + product.getPrice());
                counter++;
            }
            System.out.println("Number of positions: " + counter);
        }
    }

    public int getNumber() {
        return NUMBER;
    }
    public Map<Product, Integer> getProducts() {
        return products;
    }
}
