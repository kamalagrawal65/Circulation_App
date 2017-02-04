package lib.iitmandi.misscall1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by dibya on 28-06-2016.
 */
public class callAlert extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean started;
    Context savedContext;
    Toast toast;
    static String phoneNumber;
    static boolean ring = false;
    static boolean callReceived = false;
    static MyPhoneStateListener PhoneListener;

//    Method methodDefault = PhoneListener.getDeclaredMethod("getDefault", int.class);
//    methodDefault.setAccessible(true);
    @Override
    public void onReceive(Context context, Intent intent) {

        initialize(context);

        try {
            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            //Create Listner
            if(PhoneListener == null){
                PhoneListener = new MyPhoneStateListener();
            }
            // Register listener for LISTEN_CALL_STATE
            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
//        Log.i("state by broadcast",state);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            ring = true;
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            callReceived = true;
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            if (ring == true && callReceived == false) {

                //callMissed()
                if(phoneNumber != null && !phoneNumber.isEmpty()) {
                    Log.i("Missed Call Number", phoneNumber);
                    onMissCall(phoneNumber);
                }
                else{
                    Log.i("Missed Call Number", "null");
                }
                ring = false;
            }
            callReceived = false;
        }

        int whichSim = intent.getIntExtra("simSlot", -1);
        Log.i("sim",whichSim+"");


        try {
            endCall();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void onMissCall(String phoneNumber) {
        if(started){
            //send to server
            Log.i("server","sending number");
            new JSONTask().execute(Query.SEND_NUMBER,phoneNumber);
        }
    }

    public void initialize(Context context){
        savedContext = context;
        sharedPreferences = savedContext.getSharedPreferences("missCall", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        started = sharedPreferences.getBoolean("start",false);
//        Log.i("broadcast",started+"");
//        toast = Toast.makeText(savedContext, "BroadCast processing : " + started, Toast.LENGTH_SHORT);
//        toast.show();
    }
    public void endCall() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TelephonyManager telephonyManager = (TelephonyManager)savedContext.getSystemService(Context.TELEPHONY_SERVICE);
        Class clazz = Class.forName(telephonyManager.getClass().getName());
        Method method = clazz.getDeclaredMethod("getITelephony");
        method.setAccessible(true);
    }


    private class MyPhoneStateListener extends PhoneStateListener {

//        private Field subscriptionField;
//        private int simSlot = -1;
//        public MyPhoneStateListener(int simSlot) {
//            super();
//            try {
//                subscriptionField = this.getClass().getSuperclass().getDeclaredField("mSubscription");
//                subscriptionField.setAccessible(true);
//                try {
//                    subscriptionField.set(this, simSlot);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                this.simSlot = simSlot;
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//        }

        public void onCallStateChanged(int state, String incomingNumber) {

//            Log.d("MyPhoneListener",state+"   incoming no:"+incomingNumber);
//            Log.i("sim called",simSlot+"");
            if (state == 1) {

//                Toast toast = Toast.makeText(savedContext, msg, duration);
//                toast.show();
                if(incomingNumber != null && !incomingNumber.isEmpty()){
                    phoneNumber = incomingNumber;
                }


            }
        }
    }


}
