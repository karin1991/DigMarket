package com.karin.digmarket;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;
//
//public class ScanFragment extends Fragment {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        IntentIntegrator integrator = new IntentIntegrator(this.getActivity());
//        // use forSupportFragment or forFragment method to use fragments instead of activity
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//        integrator.setPrompt(this.getString(R.string.app_name));
////        integrator.setResultDisplayDuration(0); // milliseconds to display result on screen after scan
//        integrator.setCameraId(0);  // Use a specific camera of the device
//        integrator.initiateScan();
//    }
//
//
////    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
////        //retrieve scan result
////        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
////        ScanResultReceiver parentActivity = (ScanResultReceiver) this.getActivity();
////
////        if (scanningResult != null) {
////            //we have a result
////            String codeContent = scanningResult.getContents();
////            String codeFormat = scanningResult.getFormatName();
////            // send received data
////            parentActivity.scanResultData(codeFormat, codeContent);
////
//////        }else{
//////            // send exception
////////            parentActivity.scanResultData(new NoScanResultException(noResultErrorMsg));
//////        }
////        }
////
////
////    }
//
//}
