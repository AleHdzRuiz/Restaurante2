package restaurante.unotaxi.com.restaurante.utils;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ale on 09/03/2016.
 */
public class ConectorServices {
    Context miContexto;
    private ConectorServices conectorServices;

    public ConectorServices(Context c) {
        this.miContexto = c;
    }

    private static String inputStreamToString(InputStream is) {
        String line = "";
        String repuesta = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException ex) {
            Log.w("Aviso", ex.toString());
        }
        repuesta = total.toString();
        return repuesta;
    }

    public boolean version() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > android.os.Build.VERSION_CODES.HONEYCOMB) {
            // Toast.makeText(this, "Es mayor", Toast.LENGTH_LONG).show();
            return true;
        } else if (currentapiVersion <= android.os.Build.VERSION_CODES.HONEYCOMB) {
            // Toast.makeText(this, "es menor", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    private void verificaVersion() {
        if (version()) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads().detectDiskWrites().detectNetwork()
                    .penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                    .penaltyLog().penaltyDeath().build());
        }
    }

    public boolean activaConector(String telefono,String sO, String GCM, String marca, String modelo, String proveedor, String version, String storeProcedure) throws JSONException, IOException {
        conectorServices.verificaVersion();
        boolean resultado = false;
        HttpPost url = new HttpPost("dd");
        JSONObject jason = new JSONObject();
        jason.put("telefono", telefono);
        jason.put("sO", sO);
        jason.put("GCM", GCM);
        jason.put("marca", marca);
        jason.put("modelo", modelo);
        jason.put("proveedor", proveedor);
        jason.put("version", version);
        jason.put("storeProcedure", storeProcedure);
        jason.put("TIPO_APP", consultaIFhasTipoBD());
        jason.put("MjeAlServer", "ActivaAplicacion");

        StringEntity set = new StringEntity(jason.toString());

        url.addHeader("Acept", "application/json");
        url.addHeader("Content-Type", "application/json;charset=utf-8");
        url.setEntity(set);
        HttpClient cliente = new DefaultHttpClient();
        HttpResponse respuesta = cliente.execute(url);

        //InputStream i=respuesta.getEntity().getContent();
        //String t=inputStreamToString(i);
        JSONObject objetoJSON = new JSONObject(inputStreamToString(respuesta.getEntity().getContent()));
        JSONObject identificador = objetoJSON.getJSONObject("Respuesta");
        identificador = identificador.getJSONObject("parametros");
        resultado = identificador.getBoolean("flag");
        return resultado;
    }

    public int insertaCodigo(String telefono, String codigo) throws JSONException, IOException {
        conectorServices.verificaVersion();
        int resultado = 0;
        HttpPost url = new HttpPost("");
        JSONObject jason = new JSONObject();
        jason.put("telefono", telefono);
        jason.put("codigo", codigo);
        jason.put("TIPO_APP", consultaIFhasTipoBD());
        jason.put("MjeAlServer", "InsertaCodigo");

        StringEntity set = new StringEntity(jason.toString());

        url.addHeader("Acept", "application/json");
        url.addHeader("Content-Type", "application/json;charset=utf-8");
        url.setEntity(set);
        HttpClient cliente = new DefaultHttpClient();
        HttpResponse respuesta = cliente.execute(url);

        //InputStream i=respuesta.getEntity().getContent();
        //String t=inputStreamToString(i);
        JSONObject objetoJSON = new JSONObject(inputStreamToString(respuesta.getEntity().getContent()));
        JSONObject identificador = objetoJSON.getJSONObject("Respuesta");
        identificador = identificador.getJSONObject("parametros");
        resultado = identificador.getInt("respuesta");
        return resultado;
    }

    public int insertJornada(int idContrato, String password, int idConductor,String lat , String lang, String version) throws JSONException, IOException {
        conectorServices.verificaVersion();
        int resultado = 0;
        HttpPost url = new HttpPost("");
        JSONObject jason = new JSONObject();
        jason.put("idContrato", idContrato);
        jason.put("password", password);
        jason.put("idConductor", idConductor);
        jason.put("lat", lat);
        jason.put("lang", lang);
        jason.put("version", version);
        jason.put("TIPO_APP", consultaIFhasTipoBD());
        jason.put("MjeAlServer", "InsertaCodigo");

        StringEntity set = new StringEntity(jason.toString());

        url.addHeader("Acept", "application/json");
        url.addHeader("Content-Type", "application/json;charset=utf-8");
        url.setEntity(set);
        HttpClient cliente = new DefaultHttpClient();
        HttpResponse respuesta = cliente.execute(url);

        //InputStream i=respuesta.getEntity().getContent();
        //String t=inputStreamToString(i);
        JSONObject objetoJSON = new JSONObject(inputStreamToString(respuesta.getEntity().getContent()));
        JSONObject identificador = objetoJSON.getJSONObject("Respuesta");
        identificador = identificador.getJSONObject("parametros");
        resultado = identificador.getInt("respuesta");
        return resultado;
    }


    private String consultaIFhasTipoBD() {
        ConexionBD con = new ConexionBD(miContexto);
        String tipo = con.getCountTipoBd();
        if (tipo.equals("Pro")) {
            con.close();
            return "Pro";
        } else if (tipo.equals("Demo")) {
            con.close();
            return "Demo";
        }
        con.close();
        return "";
    }


}