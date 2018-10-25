package com.karin.digmarket;

public interface ScanResultReceiver {

   public void scanResultData(String codeFormat, String codeContent);

//    public void scanResultData(NoScanResultException noScanData);
}
