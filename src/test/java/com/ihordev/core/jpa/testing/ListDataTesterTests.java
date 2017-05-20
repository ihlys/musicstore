package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ListDataTesterTests implements WithBDDMockito {

    class Item {

        private Item itemMock;

        Item(Item itemMock) {
            this.itemMock = itemMock;
        }

        String getStringProperty() {
            return itemMock.getStringProperty();
        }
    }

    private Item createItemMockAndAddToListStub(List<Item> list, String itemStringPropertyValue) {
        Item itemMock = mock(Item.class);
        given(itemMock.getStringProperty()).willReturn(itemStringPropertyValue);
        Item itemStub = new Item(itemMock);
        list.add(itemStub);
        return itemMock;
    }

    @Test
    public void shouldTestIfTwoListsAreEqual() {
        List<Item> list1 = new ArrayList<>();
        Item list1Item1_mock = createItemMockAndAddToListStub(list1, "item#1");
        Item list1Item2_mock = createItemMockAndAddToListStub(list1, "item#2");

        List<Item> list2 = new ArrayList<>();
        Item list2Item1_mock = createItemMockAndAddToListStub(list2, "item#1");
        Item list2Item2_mock = createItemMockAndAddToListStub(list2, "item#2");

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<List<Item>> objectsWithListDataTester =
                dataTesterCreator.createForClass(new GenericClass<List<Item>>(){});
        boolean areEqual = objectsWithListDataTester.areEqual(list1, list2);

        then(list1Item1_mock).should(times(1)).getStringProperty();
        then(list1Item1_mock).shouldHaveNoMoreInteractions();
        then(list1Item2_mock).should(times(1)).getStringProperty();
        then(list1Item2_mock).shouldHaveNoMoreInteractions();

        then(list2Item1_mock).should(times(1)).getStringProperty();
        then(list2Item1_mock).shouldHaveNoMoreInteractions();
        then(list2Item2_mock).should(times(1)).getStringProperty();
        then(list2Item2_mock).shouldHaveNoMoreInteractions();

        Assert.assertTrue(areEqual);
    }

}
