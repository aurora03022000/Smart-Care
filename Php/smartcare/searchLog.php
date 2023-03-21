<?php
if(isset($_POST['log'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $wordToFind = " - ";

    $log = validate($_POST['log'] );

    if(strpos($log, $wordToFind) !== false){
        $newString = str_replace(" - ", "`-*", $log);  //replacing the characters before and after dash or - because we spaces should not be uses because dates also have spaces

        $date = strtok($newString, '`'); //getting only the date by getting only the characters up until back tick or `
    
        $toRemove = $date."`-*";//getting all the characters before the time which is needed for getting the time kay kinanglan ta nga iremove ang tanan nga characters 
        //asta time nalang kag * mabilin example
    
        $remove = preg_replace("/$toRemove+/","", $newString);//removing the characters asta time nalang ma bilin kag * sa una example *5:30
    
        $removeFirstCharacter = substr($remove, 1); //removing the first character which is the * example *5:30
    
        $time = $removeFirstCharacter;
    
        $sql = "SELECT * FROM patient_logs WHERE date='$date' and time='$time' ORDER BY `date` ASC";
            $result=mysqli_query($conn, $sql);
        
            if($result){
        
                if($result->num_rows > 0){
                    while($row = mysqli_fetch_assoc($result)){
                        echo $row['time']."`".$row['pulse_rate']."`".$row['oxygen_saturation']."`".$row['body_temperature']."`".$row['blood_pressure']."`".$row['respiratory_rate']."`".$row['room_temperature']."`".$row['humidity']."`".$row['air_quality']."`".$row['date']."`".$row['id']."%";
                    }
                }
        
                else{
                    echo 'empty';
                }
                
            }
        
            else{
                echo 'failure';
            }
    } 
    
    else{
        echo 'empty';
    }
}
?>