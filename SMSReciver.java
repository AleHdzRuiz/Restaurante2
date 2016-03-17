package restaurante.unotaxi.com.restaurante;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

/**
 * Created by Ale on 09/03/2016.
 */
public class SMSReciver extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();

    public String telefono, numSim;
    private static Intent iBroadcast_sms = new Intent("mensaje_sms");

    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage
                            .createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage
                     .getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();
                    enviaCodigo(message);
                }
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
            // enviarBD();
        }
    }

    private void enviaCodigo(String mensaje){
        mensaje=mensaje.trim();
        String codigo=mensaje.substring(mensaje.length()-5,mensaje.length());
        iBroadcast_sms.putExtra("action","1");
        iBroadcast_sms.putExtra("action_code",codigo);
        LocalBroadcastManager.getInstance(inserta_codigo_verificacion.context).sendBroadcast(iBroadcast_sms);
    }
}
