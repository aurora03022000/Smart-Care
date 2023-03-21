<?php
    // $date_str = "2012-11-20";
    // $timestamp = strtotime($date_str);
    
    // $formatted_date = date('F d, Y', $timestamp);

    // echo $formatted_date;

    // date_default_timezone_set("Asia/Manila");
    // $date = date("h:i a");
    
    
    // $formatted_date = date('F d, Y');
    // require_once "conn.php";
    // require_once "validate.php";

    // $sql1 = "SELECT * FROM patient_logs ORDER BY date ASC";
    //         $result1 = mysqli_query($conn, $sql1);

    //         if($result1){
    //             while($row = mysqli_fetch_assoc($result1)){
    //                 echo $row['date'];
    //             }
    //         }



    // $string = 'September 20, 2022 - 5:30pm';

    // $newString = str_replace(" - ", "`-*", $string);  //replacing the characters before and after dash or - because we spaces should not be uses because dates also have spaces

    // $date = strtok($newString, '`'); //getting only the date by getting only the characters up until back tick or `

    // $toRemove = $date."`-*";//getting all the characters before the time which is needed for getting the time kay kinanglan ta nga iremove ang tanan nga characters 
    // //asta time nalang kag * mabilin example

    // $remove = preg_replace("/$toRemove+/","", $newString);//removing the characters asta time nalang ma bilin kag * sa una example *5:30

    // $removeFirstCharacter = substr($remove, 1); //removing the first character which is the * example *5:30

    // $time = $removeFirstCharacter;// assigning value to time




    // $wordToFind = " - ";

    // $log = "September 20, 2022 - 5:30 pm";

    // if(strpos($log, $wordToFind) !== false){
    //     echo 'found';
    // }

    // else{
    //     $newString = str_replace(" - ", "`-*", $log);  //replacing the characters before and after dash or - because we spaces should not be uses because dates also have spaces

    //     $date = strtok($newString, '`'); //getting only the date by getting only the characters up until back tick or `
    
    //     $toRemove = $date."`-*";//getting all the characters before the time which is needed for getting the time kay kinanglan ta nga iremove ang tanan nga characters 
    //     //asta time nalang kag * mabilin example
    
    //     $remove = preg_replace("/$toRemove+/","", $newString);//removing the characters asta time nalang ma bilin kag * sa una example *5:30
    
    //     $removeFirstCharacter = substr($remove, 1); //removing the first character which is the * example *5:30
    
    //     $time = $removeFirstCharacter;
    // }

   
    // $birthdate = "2022-09-07";
    // $date = new DateTime($birthdate);
    // $newDate = $date->format('F d, Y');

    // echo $newDate;
   

    
    // $string1 = "air:50celcius:27.70humidity:95.00";

    // $stringHumidity = strstr($string1, 'humidity:');

    // $newStringWithoutHumidity = str_replace($stringHumidity,"", $string1);

    // $stringCelcius = strstr($newStringWithoutHumidity, 'celcius:');

    // $newStringWithoutCelcius = str_replace($stringCelcius,"", $newStringWithoutHumidity);

    // $stringAir = $newStringWithoutCelcius;

    // $airQualityFinal = str_replace("air:","", $stringAir);
    // $humidityFinal = str_replace("humidity:","", $stringHumidity);
    // $celciusFinal = str_replace("celcius:","", $stringCelcius);

    
    //     echo "HELLO";
    // date_default_timezone_set('Asia/Manila');
    // $myTime = date("H:i");
    // $myDate = date("d-m-y");

    // echo $myDate;
    // require_once "conn.php";
    // require_once "validate.php";
    
    // $selectedValue = "Medical Staff Name";
    // $searchValue = "Marlou";

    // if($selectedValue == "Medical Staff Name"){
    //     $sql1 = "SELECT * FROM user WHERE staff_name LIKE '%$searchValue%'";
    //     $result1 = $conn->query($sql1); 
            
    //     if($result1){
    //         while($row = mysqli_fetch_assoc($result1)){
    //             echo $row['user_id'].'`'.$row['staff_name'].'`'.$row['username'].'`'.$row['birthdate'].'`'.$row['gender'].'`'.$row['address'].'`'.$row['number'].'`';
    //         }
    //     }

    //     else{
    //         echo 'failure';
    //     }
    // }
    // $selectedValue = "3/2/2000";
    // $birthdate = new DateTime($selectedValue);
    // $birthdateFinal = $birthdate->format('F d, Y');

    // echo $birthdateFinal;

    // $string="Marlou (User ID: 1)";  //Considering the string like this
    // $new = substr($string, -2); // returns "s"
    // $string1 = substr_replace($new, '', 1, 1);


    // echo $string1;

    $string = "10:00 am";
    $new = substr($string, 0, 2);


    echo $new;

?>