package com.booksms.book.core.domain.entity;

import jakarta.persistence.*;
import jakarta.ws.rs.BadRequestException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Book extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String name;
    private Integer chapter;
    private Integer distributorId;
    private BigDecimal price;
    private String image;
    private Boolean isInStock = false;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @OneToMany(mappedBy = "book")
    private Set<InventoryBook> inventoryBooks;

    public Integer getAvailableQuantity(Address destination){
        if(inventoryBooks.isEmpty()){
            return 0;
        }

        if(destination == null){
            return inventoryBooks.stream().toList().get(0).getAvailableQuantity();
        }

        InventoryBook nearestInventory = findNearestInventory(destination);

        if(nearestInventory == null){
            return inventoryBooks.stream().toList().get(0).getAvailableQuantity();
        }

        return nearestInventory.getAvailableQuantity();
               
    }

    public Integer getAvailableQuantity(){
        return getAvailableQuantity(null);
    }

    public void setAvailableQuantity(Integer quantity,Address destination){
        if(quantity == null){
            throw new BadRequestException("Quantity cannot be null");
        }
        InventoryBook updateInventory = null;
        if(destination == null){
            updateInventory = inventoryBooks.stream().toList().get(0);
        }else{
            updateInventory = inventoryBooks.stream()
                    .filter(inventoryBook -> inventoryBook.getInventory()
                            .getAddress().equals(destination)).findFirst().get();
        }

        updateInventory.setAvailableQuantity(quantity);
    }

    public void setAvailableQuantity(Integer quantity){
        setAvailableQuantity(quantity, null);
    }

    private InventoryBook findNearestInventory(Address destination){
        if(inventoryBooks.isEmpty()){
            return null;
        }
        List<InventoryBook> listInventoryWithinCity = inventoryBooks.stream()
                .filter(inventory -> inventory.getInventory().getAddress().getCity().equals(destination.getCity()))
                .toList();

        if(listInventoryWithinCity.isEmpty()){
            return null;
        }
        if(listInventoryWithinCity.size() == 1){
            return listInventoryWithinCity.get(0);
        }

        // find nearest
        return null;
    }
}
