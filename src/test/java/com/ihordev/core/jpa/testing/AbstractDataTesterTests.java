package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Test;


public class AbstractDataTesterTests implements WithBDDMockito {

    static class ObjectA {

        private ObjectA objectAMock;

        public ObjectA(ObjectA objectAMock) {
            this.objectAMock = objectAMock;
        }

        ObjectB getObjectB() {
            return objectAMock.getObjectB();
        }
    }

    static class ObjectB {

        private ObjectB objectBMock;

        public ObjectB(ObjectB objectBMock) {
            this.objectBMock = objectBMock;
        }

        ObjectA getObjectA() {
            return objectBMock.getObjectA();
        }
    }

    //       A1              A2
    //     / |               |  \
    //    |  |               |   |
    //     \ |               |  /
    //       B1              B2
    @Test
    public void shouldTestIfTwoObjectsAreEqualIfThereAreCircularReferences1() {
        ObjectA objectAMock1 = mock(ObjectA.class);
        ObjectA objectAStub1 = new ObjectA(objectAMock1);
        ObjectB objectBMock1 = mock(ObjectB.class);
        ObjectB objectBStub1 = new ObjectB(objectBMock1);

        ObjectA objectAMock2 = mock(ObjectA.class);
        ObjectA objectAStub2 = new ObjectA(objectAMock2);
        ObjectB objectBMock2 = mock(ObjectB.class);
        ObjectB objectBStub2 = new ObjectB(objectBMock2);

        given(objectAMock1.getObjectB()).willReturn(objectBStub1);
        given(objectBMock1.getObjectA()).willReturn(objectAStub1);
        given(objectAMock2.getObjectB()).willReturn(objectBStub2);
        given(objectBMock2.getObjectA()).willReturn(objectAStub2);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<ObjectA> tester = dataTesterCreator.createForClass(new GenericClass<ObjectA>(){});

        tester.areEqual(objectAStub1, objectAStub2);

        then(objectAMock1).should(times(1)).getObjectB();
        then(objectAMock1).shouldHaveNoMoreInteractions();
        then(objectBMock1).should(times(1)).getObjectA();
        then(objectBMock1).shouldHaveNoMoreInteractions();

        then(objectAMock2).should(times(1)).getObjectB();
        then(objectAMock2).shouldHaveNoMoreInteractions();
        then(objectBMock2).should(times(1)).getObjectA();
        then(objectBMock2).shouldHaveNoMoreInteractions();
    }

    //     A1 - -          A2
    //   / |     \         |
    //  |  |      - -      |
    //   \ |          \    |
    //     B1          - - B2
    @Test
    public void shouldTestIfTwoObjectsAreEqualIfThereAreCircularReferences2() {
        ObjectA objectAMock1 = mock(ObjectA.class);
        ObjectA objectAStub1 = new ObjectA(objectAMock1);
        ObjectB objectBMock1 = mock(ObjectB.class);
        ObjectB objectBStub1 = new ObjectB(objectBMock1);

        ObjectA objectAMock2 = mock(ObjectA.class);
        ObjectA objectAStub2 = new ObjectA(objectAMock2);
        ObjectB objectBMock2 = mock(ObjectB.class);
        ObjectB objectBStub2 = new ObjectB(objectBMock2);

        given(objectAMock1.getObjectB()).willReturn(objectBStub1);
        given(objectBMock1.getObjectA()).willReturn(objectAStub1);
        given(objectAMock2.getObjectB()).willReturn(objectBStub2);
        given(objectBMock2.getObjectA()).willReturn(objectAStub1);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<ObjectA> tester = dataTesterCreator.createForClass(new GenericClass<ObjectA>(){});

        tester.areEqual(objectAStub1, objectAStub2);

        then(objectAMock1).should(times(1)).getObjectB();
        then(objectAMock1).shouldHaveNoMoreInteractions();
        then(objectBMock1).should(times(1)).getObjectA();
        then(objectBMock1).shouldHaveNoMoreInteractions();

        then(objectAMock2).should(times(1)).getObjectB();
        then(objectAMock2).shouldHaveNoMoreInteractions();
        then(objectBMock2).should(times(1)).getObjectA();
        then(objectBMock2).shouldHaveNoMoreInteractions();
    }

    //     A1 - -       - - A2
    //     |     \     /    |
    //     |       - -      |
    //     |     /     \    |
    //     B1 - -       - - B2
    @Test
    public void shouldTestIfTwoObjectsAreEqualIfThereAreCircularReferences3() {
        ObjectA objectAMock1 = mock(ObjectA.class);
        ObjectA objectAStub1 = new ObjectA(objectAMock1);
        ObjectB objectBMock1 = mock(ObjectB.class);
        ObjectB objectBStub1 = new ObjectB(objectBMock1);

        ObjectA objectAMock2 = mock(ObjectA.class);
        ObjectA objectAStub2 = new ObjectA(objectAMock2);
        ObjectB objectBMock2 = mock(ObjectB.class);
        ObjectB objectBStub2 = new ObjectB(objectBMock2);

        given(objectAMock1.getObjectB()).willReturn(objectBStub1);
        given(objectBMock1.getObjectA()).willReturn(objectAStub2);
        given(objectAMock2.getObjectB()).willReturn(objectBStub2);
        given(objectBMock2.getObjectA()).willReturn(objectAStub1);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<ObjectA> tester = dataTesterCreator.createForClass(new GenericClass<ObjectA>(){});

        tester.areEqual(objectAStub1, objectAStub2);

        then(objectAMock1).should(times(2)).getObjectB();
        then(objectAMock1).shouldHaveNoMoreInteractions();
        then(objectBMock1).should(times(2)).getObjectA();
        then(objectBMock1).shouldHaveNoMoreInteractions();

        then(objectAMock2).should(times(2)).getObjectB();
        then(objectAMock2).shouldHaveNoMoreInteractions();
        then(objectBMock2).should(times(2)).getObjectA();
        then(objectBMock2).shouldHaveNoMoreInteractions();
    }

    //     A1         - -  A2         - - A3
    //     |        /      |        /     |
    //     |       /       |       /      |
    //     |      /        |      /       |
    //     B1 - -          B2 - -         B3 - - null
    @Test
    public void shouldTestIfTwoObjectsAreEqualIfThereAreCircularReferences4() {
        ObjectA objectAMock1 = mock(ObjectA.class);
        ObjectA objectAStub1 = new ObjectA(objectAMock1);
        ObjectB objectBMock1 = mock(ObjectB.class);
        ObjectB objectBStub1 = new ObjectB(objectBMock1);

        ObjectA objectAMock2 = mock(ObjectA.class);
        ObjectA objectAStub2 = new ObjectA(objectAMock2);
        ObjectB objectBMock2 = mock(ObjectB.class);
        ObjectB objectBStub2 = new ObjectB(objectBMock2);

        ObjectA objectAMock3 = mock(ObjectA.class);
        ObjectA objectAStub3 = new ObjectA(objectAMock3);
        ObjectB objectBMock3 = mock(ObjectB.class);
        ObjectB objectBStub3 = new ObjectB(objectBMock3);

        given(objectAMock1.getObjectB()).willReturn(objectBStub1);
        given(objectBMock1.getObjectA()).willReturn(objectAStub2);
        given(objectAMock2.getObjectB()).willReturn(objectBStub2);
        given(objectBMock2.getObjectA()).willReturn(objectAStub3);
        given(objectAMock3.getObjectB()).willReturn(objectBStub3);
        given(objectBMock3.getObjectA()).willReturn(null);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<ObjectA> tester = dataTesterCreator.createForClass(new GenericClass<ObjectA>(){});

        tester.areEqual(objectAStub1, objectAStub2);

        then(objectAMock1).should(times(1)).getObjectB();
        then(objectAMock1).shouldHaveNoMoreInteractions();
        then(objectBMock1).should(times(1)).getObjectA();
        then(objectBMock1).shouldHaveNoMoreInteractions();

        then(objectAMock2).should(times(2)).getObjectB();
        then(objectAMock2).shouldHaveNoMoreInteractions();
        then(objectBMock2).should(times(2)).getObjectA();
        then(objectBMock2).shouldHaveNoMoreInteractions();

        then(objectAMock3).should(times(1)).getObjectB();
        then(objectAMock3).shouldHaveNoMoreInteractions();
        then(objectBMock3).should(times(1)).getObjectA();
        then(objectBMock3).shouldHaveNoMoreInteractions();
    }
}
