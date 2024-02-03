package ecomarkets.domain.core.basket;


import ecomarkets.domain.core.partner.PartnerId;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Entity
public class Basket extends PanacheEntity {
 
    private PartnerId partnerId;

    private LocalDateTime creationDate;
    
    private LocalDateTime reservedDate;
    
    private LocalDateTime deliveredDate;
    
    @ElementCollection
    private Collection<BasketItem> items;

    private Basket(){}
        
    public static Basket of(PartnerId partnerId){
        Basket result = new Basket();
        result.creationDate = LocalDateTime.now();
        result.partnerId = partnerId;
        result.items = new ArrayList<>();
        return result;
    }

    public BasketEvent reserveBasket(){
        if(this.id == null){
            throw new IllegalStateException("Basket not created yet!");
        }

        if(this.items == null || this.items.isEmpty()){
            throw new IllegalStateException("There are no items added to the Basket!");
        }

        this.reservedDate = LocalDateTime.now();
        return new BasketEvent(basketId(), BasketEvent.EventType.RESERVED);
    }
    
    public void deliverBasket(){
        this.deliveredDate = LocalDateTime.now();
    }

    public LocalDateTime getCreationDate(){
        return this.creationDate;
    }
    
    public Optional<LocalDateTime> getReservedDate(){
        return Optional.ofNullable(this.reservedDate);
    }
    
    public Optional<LocalDateTime> getDeliveredDate(){
        return Optional.ofNullable(this.deliveredDate);
    }
    
    public PartnerId getPartnerId(){
        return this.partnerId;
    }

    public Collection<BasketItem> getItems(){
        return new ArrayList<>(this.items);
    }

    public void addItem(BasketItem item){
        if(this.reservedDate != null){
            throw new IllegalStateException("Basket Already scheduled to delivery.");
        }

        if(this.items == null){
            throw new IllegalArgumentException("product is null");
        }

        this.items.add(item);
    }

    public void addItems(Collection<BasketItem> items){
        if(items == null)
            return;

        items.forEach(this::addItem);
    }

    public Double totalPayment(){
        return this.items.stream().mapToDouble(BasketItem::totalPayment).sum();
    }

    public BasketId basketId(){
        return BasketId.of(this.id);
    }


}
