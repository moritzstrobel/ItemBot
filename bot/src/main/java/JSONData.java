import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Deprecated
public class JSONData {

    @SuppressWarnings("unchecked")
    public static boolean addMemberEntry(String name, String startDate, String endDate) {

        if(checkDateFormat(startDate) || checkDateFormat(endDate)){return false;}

        JSONParser jsonParser = new JSONParser();
        JSONArray memberList;

        try (FileReader reader = new FileReader("absencedata.json"))
        {
            Object readObj = jsonParser.parse(reader);
            JSONObject readJSONObj = (JSONObject) readObj;

            JSONObject memberDetails = new JSONObject();
            memberDetails.put("name", name);
            memberDetails.put("startDate", startDate);
            memberDetails.put("endDate", endDate);

            Integer hash = (name + startDate + endDate).hashCode();
            memberDetails.put("hash",hash);

            memberList = (JSONArray) readJSONObj.get("absence");
            memberList.add(memberDetails);

            JSONObject writeJSONObj = new JSONObject();
            writeJSONObj.put("absence", memberList);

            try (FileWriter file = new FileWriter("absencedata.json")) {
                //We can write any JSONArray or JSONObject instance to the file
                file.write(writeJSONObj.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        //Creates new File with the format: {"absence":[Array of AbsenceMembers]}
        } catch (FileNotFoundException e) {
            failedToFindFile(name,startDate,endDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //copies File to archive if File can't be parsed
        catch (ParseException e){
            failedToParseJSON();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Member> getMemberList(){
        JSONParser jsonParser = new JSONParser();
        ArrayList<Member> memberArrayList = new ArrayList<>();

        try (FileReader reader = new FileReader("absencedata.json"))
        {
            Object readObj = jsonParser.parse(reader);
            JSONObject readJSONObj = (JSONObject) readObj;

            JSONArray memberList = (JSONArray) readJSONObj.get("absence");

            for (JSONObject s : (Iterable<JSONObject>) memberList) {

                memberArrayList.add(
                        new Member(
                                Integer.parseInt(s.get("hash").toString()),
                                s.get("name").toString(),
                                s.get("startDate").toString(),
                                s.get("endDate").toString()

                        )
                );
            }


        } catch (FileNotFoundException e) {
            failedToFindFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e){
            failedToParseJSON();
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        return memberArrayList;
    }

    public static void replaceMemberList(ArrayList<Member> memberArrayList){
        try {
            FileWriter writer = new FileWriter("absencedata.json");
            String absencendata = "{\"absence\":[";
            for (Member absenceMember: memberArrayList) {
                absencendata = absencendata.concat(absenceMember.toJSON());
            }
            absencendata = absencendata.concat("]}");
            writer.write(absencendata);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Member getSpecificMember(Integer hash){
        ArrayList<Member> memberList = getMemberList();

        for (Member absenceMember:memberList) {
            if(Objects.equals(absenceMember.getId(), hash)){return absenceMember;}
        }
        return null;
    }

    public static void rmSpecificMember(Integer hash){
        ArrayList<Member> memberList = getMemberList();
        for (Member absenceMember: memberList) {
            if(Objects.equals(absenceMember.getId(), hash)){
                memberList.remove(absenceMember);
                replaceMemberList(memberList);
                return;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void failedToFindFile(String name, String startDate, String endDate){
        try (FileWriter file = new FileWriter("absencedata.json")) {
            JSONArray memberList = new JSONArray();

            JSONObject memberJSON = new JSONObject();
            JSONObject memberDetails = new JSONObject();
            memberDetails.put("name", name);
            memberDetails.put("startDate", startDate);
            memberDetails.put("endDate", endDate);

            Integer hash = (name + startDate + endDate).hashCode();
            memberDetails.put("hash",hash);

            memberList.add(memberDetails);

            memberJSON.put("absence",memberList);

            file.write(memberJSON.toJSONString());
            file.flush();
        } catch (IOException g) {
            g.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void failedToFindFile(){
        try (FileWriter file = new FileWriter("absencedata.json")) {
            JSONArray memberList = new JSONArray();

            JSONObject memberJSON = new JSONObject();
            memberJSON.put("absence",memberList);

            file.write(memberJSON.toJSONString());
            file.flush();
        } catch (IOException g) {
            g.printStackTrace();
        }
    }

    private static void failedToParseJSON(){
        File original = new File("absencedata.json");
        File copied = new File("src/archive/copiedAbsencedata.json");
        try {
            FileUtils.copyFile(original, copied);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        original.delete();
    }

    private static boolean checkDateFormat(String date){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy", Locale.GERMAN);
            formatter.parse(date);
            return false;
        } catch (java.text.ParseException e) {
            return true;
        }
    }

    @Test
    public void testAddMember(){
        String startDateString = "13.07.13";
        String endDateString = "14.08.13";
        addMemberEntry("Jim",startDateString,endDateString);
    }

    @Test
    public void testGetMembers(){
        System.out.println(getMemberList());
    }
}
