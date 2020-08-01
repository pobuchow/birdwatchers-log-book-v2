package com.blb.main.config;

public class MockData {
    public enum SPECIES_NAME {

        BLACK_WOODPECKER("Black woodpecker"),
        EUROPEAN_GREEN_WOODPECKER("European green woodpecker"),
        MIDDLE_SPOTTED_WOODPECKER("Middle spotted woodpecker"),
        EURASIAN_THREE_TOED_WOODPECKER("Eurasian three-toed woodpecker");

        public String name;

        SPECIES_NAME(String speciesName) {
            this.name = speciesName;
        }
    }

    public enum USERS {

        DIEGO("Diego", "Dieg0!pass", "diego@old-camp.xyz"),
        LESTER("Lester", "Lester0!pass", "lester@swamp-camp.abc");

        public String username;
        public String password;
        public String email;

        USERS(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }
    }
}
