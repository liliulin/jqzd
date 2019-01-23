package com.mjitech.qa.test;

import java.util.List;

public class BatchsBean {

    private List<Skus> skus;
    private int count;
    private int sellOrderId;
    private int number;
    private int outStatus;
    private int id;
    private int outBatchId;
    private int skuCount;
    private int status;
    public void setSkus(List<Skus> skus) {
         this.skus = skus;
     }
     public List<Skus> getSkus() {
         return skus;
     }

    public void setCount(int count) {
         this.count = count;
     }
     public int getCount() {
         return count;
     }

    public void setSellOrderId(int sellOrderId) {
         this.sellOrderId = sellOrderId;
     }
     public int getSellOrderId() {
         return sellOrderId;
     }

    public void setNumber(int number) {
         this.number = number;
     }
     public int getNumber() {
         return number;
     }

    public void setOutStatus(int outStatus) {
         this.outStatus = outStatus;
     }
     public int getOutStatus() {
         return outStatus;
     }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setOutBatchId(int outBatchId) {
         this.outBatchId = outBatchId;
     }
     public int getOutBatchId() {
         return outBatchId;
     }

    public void setSkuCount(int skuCount) {
         this.skuCount = skuCount;
     }
     public int getSkuCount() {
         return skuCount;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

}