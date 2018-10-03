package com.example.sunidhi.inclass04;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sunidhi on 29-Sep-18.
 */

public class Results implements Serializable {
    List<ResultsValue> results = null;

    @Override
    public String toString() {
        return "Results{" +
                "results=" + results +
                '}';
    }

    public List<ResultsValue> getResults() {
        return results;
    }

    public void setResults(List<ResultsValue> results) {
        this.results = results;
    }

    class ResultsValue {
        String discount;
        String name;
        String photo;
        String price;
        String region;
        String dataurl;

        public String getDataurl() {
            return dataurl;
        }

        public void setDataurl(String dataurl) {
            this.dataurl = dataurl;
        }



        @Override
        public String toString() {
            return "ResultsValue{" +
                    "discount='" + discount + '\'' +
                    ", name='" + name + '\'' +
                    ", photo='" + photo + '\'' +
                    ", price='" + price + '\'' +
                    ", region='" + region + '\'' +
                    ", dataurl='" + dataurl + '\'' +
                    '}';
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
    }
}
