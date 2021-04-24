package Server;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Config.EditConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.paulvogel.multicraftapi.MulticraftAPI;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class Commands {
    private static String FTPserver = getItemFromServerConfig("FTPserver");
    private static int FTPport = Integer.parseInt(getItemFromServerConfig("FTPport"));
    private static FTPClient ftpClient = new FTPClient();
    private static String ApiUrl = getItemFromServerConfig("ApiUrl");
    private static String ApiUsername = getItemFromServerConfig("ApiUsername");
    private static String ApiKey = getItemFromServerConfig("ApiKey");
    private static MulticraftAPI api = new MulticraftAPI(ApiUrl, ApiUsername, ApiKey);
    private static HashMap<String,String> apituloste=new HashMap<>();
    public static boolean FTPonline =false;


    public static String getItemFromServerConfig(String avain){
        return EditConfig.readServerConfig().get(avain);
    }
    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);

            }
        }
    }
    public static boolean isOnline() throws IOException {
        //my user id 94568 and server id 184337
        apituloste.put("id","184337");
        apituloste.put("player_list","true");
       //https://www.multicraft.org/site/docs/api#3


        JsonNode parent= new ObjectMapper().readTree(api.call("getServerStatus",apituloste).toString());
        String content = parent.path("status").asText();
        System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber());
        if(content.equals("online")){
            System.out.println("Multicraftin api kutsu palautui onnistuneesti ja discord bottisi on "+ content);
            return true;
        }
        return false;
    }
    public static void yhdista(String email, String pw) {

        api.call("startServer",apituloste);//lähettää botin käynnistys käskyn palvelimelle, paluu jarboot mainiin.
        try {
            ftpClient.connect(FTPserver, FTPport);
            showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return;
            }
            boolean success = ftpClient.login(email, pw);
            showServerReply(ftpClient);
            if (!success) {
                System.out.println("Kirjautuminen ei onnistunut. Tarkista käyttäjätunnus ja salasana.");

            } else {
                FTPonline=true;
                System.out.println("LOGGED IN SERVER");
            }
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        }
    }
    public static void laheta(String lahetettava) {



        try {

            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File firstLocalFile = new File("src\\main\\resources\\"+lahetettava);

            String firstRemoteFile = "src/main/resources/"+lahetettava;
            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The file was uploaded successfully.");
            }


        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public static String lueTxtTiedosto(String luettavaTdsto) throws IOException {
        Path fileName = Path.of("src/main/resources/"+luettavaTdsto);
        return Files.readString(fileName);
    }

    public static void poistaTdsto(String poistettava) {
        File myObj = new File("src/main/resources/"+poistettava);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    public static void teeJaKirjoitaTdstoon(String kirjoitettava, String tdstoNimi) {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/"+tdstoNimi);
            myWriter.write(kirjoitettava);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
    public static boolean onkoTdstoa(String tarkistettava) {
        File tmpDir = new File("src/main/resources/"+tarkistettava);
        return  tmpDir.exists();

    }

}
