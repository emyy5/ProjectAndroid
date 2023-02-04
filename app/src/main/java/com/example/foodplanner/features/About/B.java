package com.example.foodplanner.features.About;

public class B implements MyInterface{
    B(){
        A a = new A();

        // Anonymous(مجهول)
        a.getData(new MyInterface() {
            @Override
            public void fetchdata(String data) {
                //* write any thing
            }
        });

        // Lambda exception
        a.getData(data -> {
            //* write any thing
        });



        // Declared(معرف)
        MyInterface myInterface = new MyInterface() {
            @Override
            public void fetchdata(String data) {
                //* write any thing
            }
        };

        a.getData(myInterface);


        // Implemented
        a.getData(this);

    }

    @Override
    public void fetchdata(String data) {

    }
}
