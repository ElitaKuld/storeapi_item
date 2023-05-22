package com.example.storeapi_item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path="/items")
public class ItemController {
    @Autowired
    private final ItemRepository itemRepository;
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // http://localhost:8082/items/all (Denna returnerar alla varor)
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Item> getAllItems() {
        log.info("GET-ting all items");
        // This returns a JSON or XML with the items
        return itemRepository.findAll();
    }

    // http://localhost:8082/items/get/{id} (Denna returnerar en vara baserat på varans id)
    @GetMapping(path="get/{id}")
    public @ResponseBody Item getItemById(@PathVariable long id) {
        log.info("GET-ting the item with the id " + id);
        return itemRepository.findById(id).get();
    }

    // http://localhost:8082/items/add (Denna endpoint skapar ny vara)
    @RequestMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewItem (@RequestParam String name
            , @RequestParam Double price) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Item item = new Item();
        item.setName(name);
        item.setPrice(price);

        if (itemAlreadyExists(item)) {
            log.info("ERROR: Item " + item.getName() + " for the price of " + item.getPrice() + " ALREADY EXISTS IN THE DATABASE.");
            return "ERROR: Item " + item.getName() + " for the price of " + item.getPrice() + " ALREADY EXISTS IN THE DATABASE.";
        }

        itemRepository.save(item);
        log.info("Item " + item.getName() + " for the price of " + item.getPrice() + " has been added to the database.");
        return "Item " + item.getName() + " for the price of " + item.getPrice() + " has been added to the database.";
    }

    @PostMapping(path = "/add/body") // Map ONLY POST Requests
    public @ResponseBody String addNewItemViaBody(@RequestBody AddItemForm input) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Item item = new Item();
        item.setName(input.getName());
        item.setPrice(input.getPrice());

        if (itemAlreadyExists(item)) {
            log.info("ERROR: Item " + item.getName() + " for the price of " + item.getPrice() + " ALREADY EXISTS IN THE DATABASE.");
            return "ERROR: Item " + item.getName() + " for the price of " + item.getPrice() + " ALREADY EXISTS IN THE DATABASE.";
        }

        itemRepository.save(item);
        log.info("Item " + item.getName() + " for the price of " + item.getPrice() + " has been added to the database.");
        return "Item " + item.getName() + " for the price of " + item.getPrice() + " has been added to the database.";
    }

    public Boolean itemAlreadyExists(Item itemToCheck) {
        List<Item> itemsWithSameName = itemRepository.findAll().stream().filter(currentItem ->
                currentItem.getName().equals(itemToCheck.getName())).toList();
        if (itemsWithSameName.size() != 0) {
            List<Item> itemsWithTheSamePrice = itemsWithSameName.stream().filter(currentItem ->
                    Objects.equals(currentItem.getPrice(), itemToCheck.getPrice())).toList();
            return itemsWithTheSamePrice.size() != 0;
        }
        return false;
    }
}
