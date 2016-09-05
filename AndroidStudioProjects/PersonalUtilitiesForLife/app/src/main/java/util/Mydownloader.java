package util;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.TiGiaActivity;
import software.nhut.personalutilitiesforlife.constant.AppConstant;

/**
 * Created by Nhut on 7/16/2016.
 */
public class Mydownloader {
    public static String[] getExchangeRate(int bank, String link){
        String []arrayExchangeRate = new String[12];
        String result = fetchInfo(link.split(TiGiaActivity.LIMITER));
        if (bank == TiGiaActivity.ARRAYNGANHANG[0]) {
            arrayExchangeRate = parseExchangeRate0(result);
        } else if (bank == TiGiaActivity.ARRAYNGANHANG[1]) {
            arrayExchangeRate = parseExchangeRate1(result);
        } else if (bank == TiGiaActivity.ARRAYNGANHANG[2]) {
            arrayExchangeRate = parseExchangeRate2(result);
        } else if (bank == TiGiaActivity.ARRAYNGANHANG[3]) {
            arrayExchangeRate = parseExchangeRate3(result);
        } else if (bank == TiGiaActivity.ARRAYNGANHANG[4]) {
            arrayExchangeRate = parseExchangeRate4(result);
        } else if (bank == TiGiaActivity.ARRAYNGANHANG[5]) {
            arrayExchangeRate = parseExchangeRate5(result);
        } else if (bank == TiGiaActivity.ARRAYNGANHANG[6]) {
            arrayExchangeRate = parseExchangeRate6(result);
        } else if (bank == TiGiaActivity.ARRAYNGANHANG[7]) {
            arrayExchangeRate = parseExchangeRate7(result);
        } else if (bank == TiGiaActivity.ARRAYNGANHANG[8]) {
            arrayExchangeRate = parseExchangeRate8(result);
        } else if (bank == TiGiaActivity.ARRAYNGANHANG[9]) {
            arrayExchangeRate = parseExchangeRate8(result);
        }
        return arrayExchangeRate;
    }

    public static boolean getPriceList(int computerStore, String link) {
        boolean result = true;

        return result;
    }

    private static String[] parseExchangeRate0(String result) {
//        return new String[12];
        String []gia = {"2368,,,,4567,,,,9101,,,,2345", "2368,,,,4567,,,,9101,,,,2345", "2368,,,,4567,,,,9101,,,,2345", "2368,,,,4567,,,,9101,,,,2345",
                "2368,,,,4567,,,,9101,,,,2345", "2368,,,,4567,,,,9101,,,,2345", "2368,,,,4567,,,,9101,,,,2345", "2368,,,,4567,,,,9101,,,,2345",
                "2368,,,,4567,,,,9101,,,,2345", "2368,,,,4567,,,,9101,,,,2345", "2368,,,,4567,,,,9101,,,,2345", "2368,,,,4567,,,,9101,,,,2345"};

        return gia;
    }

//    int []hinh = {R.drawable.flag_of_the_united_states_s, R.drawable.flag_of_europe_s, R.drawable.flag_of_the_united_kingdom_s
//            , R.drawable.flag_of_hong_kong_s, R.drawable.flag_of_australia_s, R.drawable.flag_of_canada_s
//            , R.drawable.flag_of_japan_s, R.drawable.flag_of_new_zealand_s, R.drawable.flag_of_singapore_s
//            , R.drawable.flag_of_switzerland_s, R.drawable.flag_of_thailand_s, R.drawable.gold};
//    txtMuaTM.setText(giaCa[0]);
//    txtBanTM.setText(giaCa[1]);
//    txtMuaCK.setText(giaCa[2]);
//    txtBanCK.setText(giaCa[3]);
    public static final String MONEYTYPE[] = {"USD", "EUR", "GBP", "HKD", "AUD", "CAD", "JPY", "NZD", "SGD", "CHF", "THB", "SJC (1L)"};
    private static String[] parseExchangeRate1(String result) {
        String []arrayData = new String[12];
        int temp;
        String tString[];
        try {
            for (int i = 0; i < MONEYTYPE.length - 1; i++) {
                temp = result.indexOf(MONEYTYPE[i],
                        result.indexOf("<td style=\"text-align: center; vertical-align: middle; width: 20%\">"));
                tString = result.substring(temp, result.indexOf("</tr>", temp)).split(">");
                String muaCK = tString[4].replace(",00", "").replace("</td", "").trim();
                String muaTM = tString[6].replace(",00", "").replace("</td", "").trim();
                String ban = tString[8].replace(",00", "").replace("</td", "").trim();
                arrayData[i] = muaTM + ",,,," + ban + ",,,," + muaCK + ",,,," + ban;
            }
            temp = result.indexOf(MONEYTYPE[11], result.indexOf("<td style=\"font-weight:bold;\">"));
            tString = result.substring(temp, result.indexOf("</tr>", temp)).split(">");
            String mua = tString[2].replace(",00", "").replace("</td", "").trim();
            String ban = tString[4].replace(",00", "").replace("</td", "").trim();
            arrayData[11] = mua + ",,,," + ban + ",,,," + mua + ",,,," + ban;
        } catch (Exception e) {
            Log.i("MyTag", "parseExchangeRate1 error");
            e.printStackTrace();
        }
        return arrayData;
    }

    private static String[] parseExchangeRate2(String result) {
         String []arrayData = new String[12];
        int temp;
        String tString[];
        try {
            for (int i = 0; i < MONEYTYPE.length - 1; i++) {
                temp = result.indexOf(MONEYTYPE[i],
                        result.indexOf("<td align=\"center\">" + MONEYTYPE[i]));
                tString = result.substring(temp, result.indexOf("</tr>", temp)).split(">");
                String muaTM = tString[4].replace("</td", "").trim();
                String muaCK = tString[6].replace("</td", "").trim();
                String ban = tString[8].replace("</td", "").trim();
                arrayData[i] = muaTM + ",,,," + ban + ",,,," + muaCK + ",,,," + ban;
            }
            temp = result.indexOf("Giá vàng 9999 tại");
            tString = result.substring(temp, result.indexOf("nghìn đồng/chỉ.", temp)).split("mua: ")[1].split("nghìn đồng/chỉ, bán:");
            String mua = tString[0].trim();
            String ban = tString[1].trim();
            arrayData[11] = mua + ",,,," + ban + ",,,," + mua + ",,,," + ban;
        } catch (Exception e) {
            Log.i("MyTag", "parseExchangeRate2 error");
            e.printStackTrace();
        }
        return arrayData;
    }

    private static String[] parseExchangeRate3(String result) {
        String []arrayData = new String[12];
        int temp;
        String tString[];
        try {
            for (int i = 0; i < MONEYTYPE.length; i++) {
                Log.i("MyTag", "index " + i);
                temp = result.indexOf("<td class=\"code\">" + MONEYTYPE[i]);
                if (temp == -1) {
                    arrayData[i] = "_,,,,_,,,,_,,,,_";
                    continue;
                }
                tString = result.substring(temp, result.indexOf("</tr>", temp)).split(">");
                String muaTM = tString[5].replace("</td", "").trim(); muaTM = muaTM.substring(0, muaTM.length() - 3);
                String muaCK = tString[7].replace("</td", "").trim(); muaCK = muaCK.substring(0, muaCK.length() - 3);
                String ban = tString[9].replace("</td", "").trim(); ban = ban.substring(0, ban.length() - 3);
                arrayData[i] = muaTM + ",,,," + ban + ",,,," + muaCK + ",,,," + ban;
            }
        } catch (Exception e) {
            Log.i("MyTag", "parseExchangeRate3 error");
            e.printStackTrace();
        }
        return arrayData;
    }

    private static String[] parseExchangeRate4(String result) {
        String MONEYTYPE4[] = {"USD,50-10", "EUR", "GBP", "HKD", "AUD", "CAD", "JPY", "NZD", "SGD", "CHF", "THB", "Tỷ giá vàng"};
        String []arrayData = new String[12];
        int temp;
        String tString[];
        try {
            for (int i = 0; i < MONEYTYPE4.length - 1; i++) {
                temp = result.indexOf("><strong>" + MONEYTYPE4[i]);
                if (temp == -1) {
                    arrayData[i] = "_,,,,_,,,,_,,,,_";
                    continue;
                }
                tString = result.substring(temp, result.indexOf("</tr>", temp)).split(">");
                String muaTM = tString[7].replace("&nbsp;", "").replace("</strong", "").trim();
                String muaCK = tString[11].replace("&nbsp;", "").replace("</strong", "").trim();
                String ban = tString[15].replace("&nbsp;", "").replace("</strong", "").trim();
                arrayData[i] = muaTM + ",,,," + ban + ",,,," + muaCK + ",,,," + ban;
            }
            temp = result.indexOf("><strong>" + MONEYTYPE4[11]);
            tString = result.substring(temp, result.indexOf("</tr>", temp)).split(">");
            String mua = tString[6].replace("&nbsp;", "").replace("</strong", "").trim();
            String ban = tString[12].replace("&nbsp;", "").replace("</strong", "").trim();
            arrayData[11] = mua + ",,,," + ban + ",,,," + mua + ",,,," + ban;
        } catch (Exception e) {
            Log.i("MyTag", "parseExchangeRate4 error");
            e.printStackTrace();
        }
        return arrayData;
    }

    private static String[] parseExchangeRate5(String result) {
        String []arrayData = new String[12];
        String MONEYTYPE5[] = {"USD", "EUR", "GBP", "HKD", "AUD", "CAD", "JPY", "NZD", "SGD", "CHF", "THB", "XAU"};
        int temp;
        String tString[];
        try {
            for (int i = 0; i < MONEYTYPE5.length; i++) {
                temp = result.indexOf("{\"type\":\"" + MONEYTYPE5[i]);
                if (temp == -1) {
                    arrayData[i] = "_,,,,_,,,,_,,,,_";
                    continue;
                }
                tString = result.substring(temp, result.indexOf("\"}", temp)).split("\":\"");
                String muaTM = tString[3].replace("\",\"muack", ""); if (muaTM.trim().length() == 0) muaTM = "_";
                String muaCK = tString[4].replace("\",\"bantienmat", ""); if (muaCK.trim().length() == 0) muaCK = "_";
                String banTM = tString[5].replace("\",\"banck", ""); if (banTM.trim().length() == 0) banTM = "_";
                String banCK = tString[6]; if (banCK.trim().length() == 0) banCK = "_";
                arrayData[i] = muaTM + ",,,," + banTM + ",,,," + muaCK + ",,,," + banCK;
            }
        } catch (Exception e) {
            Log.i("MyTag", "parseExchangeRate5 error");
            e.printStackTrace();
        }
        return arrayData;
    }

    private static String[] parseExchangeRate6(String result) {
        String []arrayData = new String[12];
        String MONEYTYPE6[] = {"  USD", "  EUR", "  GBP", "  HKD", "  AUD", "  CAD", "  JPY", "  NZD", "  SGD", "  CHF", "  THB", "  XAU"};
        int temp;
        String tString[];
        try {
            for (int i = 0; i < MONEYTYPE6.length - 1; i++) {
                temp = result.indexOf(MONEYTYPE6[i],
                        result.indexOf("<td class=\"current-item text\">"));
                if (temp == -1) {
                    arrayData[i] = "_,,,,_,,,,_,,,,_";
                    continue;
                }
                tString = result.substring(temp, result.indexOf("</tr>", temp)).split(">");
                String muaTM = tString[2].replace("</td", "").trim();
                String muaCK = tString[4].replace("</td", "").trim();
                String ban = tString[6].replace("</td", "").trim();
                arrayData[i] = muaTM + ",,,," + ban + ",,,," + muaCK + ",,,," + ban;
            }
            temp = result.indexOf(MONEYTYPE6[11],
                    result.indexOf("<td class=\"current-item text\">"));
            tString = result.substring(temp, result.indexOf("</tr>", temp)).split(">");
            String mua = tString[2].replace("</td", "").trim();
            String ban = tString[4].replace("</td", "").trim();
            arrayData[11] = mua + ",,,," + ban + ",,,," + mua + ",,,," + ban;
        } catch (Exception e) {
            Log.i("MyTag", "parseExchangeRate6 error");
            e.printStackTrace();
        }
        return arrayData;
    }

    private static String[] parseExchangeRate7(String result) {
        String []arrayData = new String[12];
        int temp;
        String tString[];
        try {
            for (int i = 0; i < MONEYTYPE.length - 1; i++) {
                temp = result.indexOf(">" + MONEYTYPE[i] + "<");
                if (temp == -1) {
                    arrayData[i] = "_,,,,_,,,,_,,,,_";
                    continue;
                }
                tString = result.substring(temp, result.indexOf("</tr>", temp)).split(">");
                String muaCK = tString[5].replace("</td", "").trim();
                if (muaCK.trim().length() == 0) muaCK = "_";
                String muaTM = tString[7].replace("</td", "").trim();
                if (muaTM.trim().length() == 0) muaTM = "_";
                String ban = tString[9].replace("</td", "").trim();
                if (ban.trim().length() == 0) ban = "_";
                arrayData[i] = muaTM + ",,,," + ban + ",,,," + muaCK + ",,,," + ban;
            }
            arrayData[11] = "_,,,,_,,,,_,,,,_";
        } catch (Exception e) {
            Log.i("MyTag", "parseExchangeRate7 error");
            e.printStackTrace();
        }
        return arrayData;
    }

    private static String[] parseExchangeRate8(String result) {
        String []arrayData = new String[12];
        int temp;
        String tString[];
        try {
            for (int i = 0; i < MONEYTYPE.length - 1; i++) {
                temp = result.indexOf("<td align=\"left\">" + MONEYTYPE[i]);
                if (temp == -1) {
                    arrayData[i] = "_,,,,_,,,,_,,,,_";
                    continue;
                }
                String t = result.substring(temp, result.indexOf("<tr>", temp));
                Log.i("MyTag", "String " + t);
                tString = t.split(">");
                String muaTM = tString[4].replace("</b", "");
                if (muaTM.trim().length() == 0 || muaTM.trim().equals("0")) muaTM = "_";
                String muaCK = tString[8].replace("</b", "");
                if (muaCK.trim().length() == 0 || muaCK.trim().equals("0")) muaCK = "_";
                String ban = tString[12].replace("</b", "");
                if (ban.trim().length() == 0 || ban.trim().equals("0")) ban = "_";
                arrayData[i] = muaTM + ",,,," + ban + ",,,," + muaCK + ",,,," + ban;
            }
            arrayData[11] = "_,,,,_,,,,_,,,,_";
        } catch (Exception e) {
            Log.i("MyTag", "parseExchangeRate8 error");
            e.printStackTrace();
        }
        return arrayData;
    }

    private static String fetchInfo(String []arrayAddress) {
        String result = "";
        for (int i = 0; i < arrayAddress.length - 1; i++) {
            try {
                URL url = new URL(arrayAddress[i]);
                doTrustToCertificates();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", arrayAddress[arrayAddress.length - 1]);
                connection.setRequestProperty("User-Agent", "Personal utilities for life on " + android.os.Build.VERSION.SDK_INT);
                connection.setRequestProperty("Accept", "*/*");
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String line = br.readLine();
                StringBuilder sb = new StringBuilder();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                result += sb.toString() + TiGiaActivity.LIMITER;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    // trusting all certificate
    private static void doTrustToCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {

                    public java.security.cert.X509Certificate[] getAcceptedIssuers()
                    {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                    {
                        //No need to implement.
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                    {
                        //No need to implement.
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    /*
        download file
        hien thi % download

    */
    public static class DownloadFileTask extends AsyncTask<String, Integer, Void> {
        private ProgressBar pb;
        private String savingPath;

        public DownloadFileTask(ProgressBar pb, String savingPath) {
            this.pb = pb;
            this.savingPath = savingPath;
        }

        @Override
        protected void onPreExecute() {
            pb.setMax(100);
            pb.setProgress(0);
        }

        @Override
        protected Void doInBackground(String... downloadLink) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            for (String link : downloadLink) {
                try {
                    URL url = new URL(link);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    // expect HTTP 200 OK, so we don't mistakenly save error report instead of the file
                    Log.i("MyTag", link + " Response code " + connection.getResponseCode());
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                        throw new Exception("Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage());
                    // this will be useful to display download percentage might be -1: server did not report the length
                    int fileLength = connection.getContentLength();
                    // download the file
                    input = connection.getInputStream();
                    output = new FileOutputStream(savingPath);

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                        // publishing the progress....
                        total += count;
                        if (fileLength > 0) publishProgress((int) (total * 100 / fileLength));
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (output != null) output.close();
                        if (input != null) input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (connection != null) connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            pb.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pb.setProgress(100);
        }
    }
}
