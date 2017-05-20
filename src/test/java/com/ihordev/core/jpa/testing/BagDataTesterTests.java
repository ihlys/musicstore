package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;


public class BagDataTesterTests implements WithBDDMockito {

    class Item {

        private Item itemMock;

        Item(Item itemMock) {
            this.itemMock = itemMock;
        }

        String getStringProperty() {
            return itemMock.getStringProperty();
        }
    }


    private Item createItemMockAndAddToBagStub(Collection<Item> bag, String itemStringPropertyValue) {
        Item itemMock = mock(Item.class);
        given(itemMock.getStringProperty()).willReturn(itemStringPropertyValue);
        Item itemStub = new Item(itemMock);
        bag.add(itemStub);
        return itemMock;
    }

    @Test
    public void shouldTestIfTwoBagsAreEqual() {
        String item1StringProperty = "item#1";
        String item2StringProperty = "item#2";

        Collection<Item> bag1 = new ArrayList<>();
        Item bag1Item1_mock = createItemMockAndAddToBagStub(bag1, item1StringProperty);
        Item bag1Item2_mock = createItemMockAndAddToBagStub(bag1, item2StringProperty);

        Collection<Item> bag2 = new ArrayList<>();
        Item bag2Item1_mock = createItemMockAndAddToBagStub(bag2, item1StringProperty);
        Item bag2Item2_mock = createItemMockAndAddToBagStub(bag2, item2StringProperty);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<Collection<Item>> objectsWithBagDataTester =
                dataTesterCreator.createForClass(new GenericClass<Collection<Item>>(){});
        boolean areEqual = objectsWithBagDataTester.areEqual(bag1, bag2);

        then(bag1Item1_mock).should(times(2)).getStringProperty(); // first call to get hashcode
                                                              // second call to test equality
        then(bag1Item1_mock).shouldHaveNoMoreInteractions();
        then(bag1Item2_mock).should(times(2)).getStringProperty();
        then(bag1Item2_mock).shouldHaveNoMoreInteractions();

        then(bag2Item1_mock).should(times(2)).getStringProperty();
        then(bag2Item1_mock).shouldHaveNoMoreInteractions();
        then(bag2Item2_mock).should(times(2)).getStringProperty();
        then(bag2Item2_mock).shouldHaveNoMoreInteractions();

        Assert.assertTrue(areEqual);
    }

    @Test
    public void shouldComputeHashCodeCorrectly() {
        String item1StringProperty = "item#1";
        String item2StringProperty = "item#2";
        int expectedHashCode = item1StringProperty.hashCode() + item2StringProperty.hashCode();

        Collection<Item> bag = new ArrayList<>();
        Item bagItem1_mock = createItemMockAndAddToBagStub(bag, item1StringProperty);
        Item bagItem2_mock = createItemMockAndAddToBagStub(bag, item2StringProperty);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<Collection<Item>> objectsWithBagDataTester =
                dataTesterCreator.createForClass(new GenericClass<Collection<Item>>(){});
        int actualHashCode = objectsWithBagDataTester.getHashCode(bag);

        then(bagItem1_mock).should(times(1)).getStringProperty();
        then(bagItem1_mock).shouldHaveNoMoreInteractions();
        then(bagItem2_mock).should(times(1)).getStringProperty();
        then(bagItem2_mock).shouldHaveNoMoreInteractions();

        Assert.assertEquals(expectedHashCode, actualHashCode);
    }

    @Test
    public void shouldConvertToStringCorrectly() {
        String item1StringProperty = "item#1";
        String item2StringProperty = "item#2";

        Collection<Item> bag = new ArrayList<>();
        Item bagItem1_mock = createItemMockAndAddToBagStub(bag, item1StringProperty);
        Item bagItem2_mock = createItemMockAndAddToBagStub(bag, item2StringProperty);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<Collection<Item>> objectsWithBagDataTester =
                dataTesterCreator.createForClass(new GenericClass<Collection<Item>>(){});
        String asString = objectsWithBagDataTester.toString(bag);

        then(bagItem1_mock).should(times(1)).getStringProperty();
        then(bagItem1_mock).shouldHaveNoMoreInteractions();
        then(bagItem2_mock).should(times(1)).getStringProperty();
        then(bagItem2_mock).shouldHaveNoMoreInteractions();

        System.out.println(asString);
    }
}
