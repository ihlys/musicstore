package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class SetDataTesterTests implements WithBDDMockito {

    class Item {

        private Item itemMock;

        Item(Item itemMock) {
            this.itemMock = itemMock;
        }

        String getStringProperty() {
            return itemMock.getStringProperty();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Item)) return false;

            Item item = (Item) o;

            return getStringProperty().equals(item.getStringProperty());
        }

        @Override
        public int hashCode() {
            return getStringProperty().hashCode();
        }

    }

    private Item createItemMockAndAddToSetStub(Set<Item> set, String itemStringPropertyValue,
                                               final int[] setItem_counter) {
        Item itemMock = mock(Item.class);
        doAnswer(invocation -> {
            setItem_counter[0]++;
            return itemStringPropertyValue;
        }).when(itemMock).getStringProperty();
        Item itemStub = new Item(itemMock);
        set.add(itemStub);
        return itemMock;
    }

    @Test
    public void shouldTestIfTwoSetsAreEqual() {
        Set<Item> setA = new HashSet<>();
        final int[] setAItem1_counter = new int[1];
        final int[] setAItem2_counter = new int[1];
        createItemMockAndAddToSetStub(setA, "item#1", setAItem1_counter);
        createItemMockAndAddToSetStub(setA, "item#2", setAItem2_counter);

        Set<Item> setB = new HashSet<>();
        final int[] setBItem1_counter = new int[1];
        final int[] setBItem2_counter = new int[1];
        createItemMockAndAddToSetStub(setB, "item#1", setBItem1_counter);
        createItemMockAndAddToSetStub(setB, "item#2", setBItem2_counter);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<Set<Item>> objectsWithSetDataTester =
                dataTesterCreator.createForClass(new GenericClass<Set<Item>>(){});
        boolean areEqual = objectsWithSetDataTester.areEqual(setA, setB);

        // comparing order is unpredictable in sets
        // set would request hashcode 1 time on every item, so 1 call on property by default

        Assert.assertEquals(5, setAItem1_counter[0] + setAItem2_counter[0]);
        Assert.assertEquals(5, setBItem1_counter[0] + setBItem2_counter[0]);

        Assert.assertTrue(areEqual);
    }

}
