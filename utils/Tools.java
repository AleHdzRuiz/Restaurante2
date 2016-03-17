package restaurante.unotaxi.com.restaurante.utils;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import restaurante.unotaxi.com.restaurante.R;
public class Tools {

    public static void alertGeneric(Context context, String title, String message, String txtBtn) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_alert);

        TextView txtTitle = (TextView) dialog.findViewById(R.id.lblTitle);
        txtTitle.setText(title);

        TextView txtMessage = (TextView) dialog.findViewById(R.id.lblMessage);
        txtMessage.setText(message);

        Button btnAceptar = (Button) dialog.findViewById(R.id.btnAccept);
        btnAceptar.setText(txtBtn);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    public static void alertGeneric(Context context, String title, String message, String txtBtn, View.OnClickListener action) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_alert);

        TextView txtTitle = (TextView) dialog.findViewById(R.id.lblTitle);
        txtTitle.setText(title);

        TextView txtMessage = (TextView) dialog.findViewById(R.id.lblMessage);
        txtMessage.setText(message);

        Button btnAceptar = (Button) dialog.findViewById(R.id.btnAccept);
        btnAceptar.setText(txtBtn);
        btnAceptar.setOnClickListener(action);
        dialog.show();
    }

    public static void saveSharedPreferences(SharedPreferences sharedPreferences, String name, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(name);
        editor.putString(name, value);
        editor.commit();
    }

    public static String getSharedPreferences(SharedPreferences sharedPreferences, String name) {
        return sharedPreferences.getString(name, "");
    }


}
