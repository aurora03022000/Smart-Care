<?php
if(isset($_POST['name']) && isset($_POST['gender']) && isset($_POST['birthdate']) && isset($_POST['address']) && isset($_POST['number']) && isset($_POST['disease']) && isset($_POST['room']) && isset($_POST['device']) && isset($_POST['patientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $name = validate($_POST['name']);
    $gender = validate($_POST['gender']);
    $birthdate = validate($_POST['birthdate']);
    $address = validate($_POST['address']);
    $number = validate($_POST['number']);
    $disease = validate($_POST['disease']);
    $room = validate($_POST['room']);
    $device = validate($_POST['device']);
    $patientID = validate($_POST['patientID']);

    $sql = "UPDATE patient SET patient_name='$name', gender='$gender', birthdate='$birthdate', address='$address', disease = '$disease', number='$number', room='$room', deviceID='$device' WHERE patient_id='$patientID' and isExist=1";
    $result = $conn->query($sql);
    
    if($result){
        $sql1 = "SELECT * FROM patient WHERE patient_id = '$patientID' and isExist=1 ORDER BY patient_name ASC";
        $result1 = $conn->query($sql1);

        while($row = mysqli_fetch_assoc($result1)){
            if($row['share_id'] == "" || $row['share_id'] == " "){
                $patientName = $row['patient_name'];
                $activity = "Updated patient information named ".$patientName;
                date_default_timezone_set('Asia/Manila');
                $activityDate = date("F d, Y");
                $activityDateFinal = new DateTime($activityDate);
                $newActivityDate = $activityDateFinal->format('F d, Y');
                $activityTime = date("h:i a");
                $user = $row['encoder'];
    
                $addHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                $addHistoryResult = mysqli_query($conn, $addHistory);
    
                if($addHistoryResult){
                    echo 'success';
                }
    
                else{
                    echo 'failure';
                }
            }

            else{
                $patientName = $row['patient_name'];
                $activity = "Updated patient information named ".$patientName;
                date_default_timezone_set('Asia/Manila');
                $activityDate = date("F d, Y");
                $activityDateFinal = new DateTime($activityDate);
                $newActivityDate = $activityDateFinal->format('F d, Y');
                $activityTime = date("h:i a");
                $user = $row['share_id'];

                $addHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                $addHistoryResult = mysqli_query($conn, $addHistory);

                if($addHistoryResult){
                    echo 'success';
                }

                else{
                    echo 'failure';
                }
            }
            
        }
    }

    else{
        echo 'failure';
    }

}
?>