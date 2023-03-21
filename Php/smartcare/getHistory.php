<?php
if(isset($_POST['currentUserID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);

    $sql = "SELECT * FROM history_log WHERE user='$currentUserID' and isExist=1";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){
            date_default_timezone_set('Asia/Manila');
            $myTime = date("H:i");
            $myDate = date("d-m-y");

            if($row['date'] < $myDate){
                if($row['time'] < $myTime){
                    //delete
                }

                else{
                    //minus the time to the current time to get how many time it was registered
                    //minus the time registered to the current time and then the result will be minus to 24 and the result will be the time how lomg it was registered
                }
            }

            else{
                
            }
        }
    }

    else{
        echo 'failure';
    }

}
?>