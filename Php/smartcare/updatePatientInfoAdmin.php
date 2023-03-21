<?php
if(isset($_POST['name']) && isset($_POST['gender']) && isset($_POST['birthdate']) && isset($_POST['address']) && isset($_POST['number']) && isset($_POST['disease']) && isset($_POST['room']) && isset($_POST['device']) && isset($_POST['patientID']) && isset($_POST['encoder'])){
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
    $encoder = validate($_POST['encoder']);

    $getEquivalentIDOfEncoder = "SELECT * FROM user WHERE staff_name = '$encoder'";
    $getEquivalentIDOfEncoderResult = mysqli_query($conn, $getEquivalentIDOfEncoder);

    if($getEquivalentIDOfEncoderResult){
        while($rowEncoder = mysqli_fetch_assoc($getEquivalentIDOfEncoderResult)){
            $getPatientPreviousInfo = "SELECT * FROM patient WHERE patient_id='$patientID'";
            $getPatientPreviousInfoResult = $conn->query($getPatientPreviousInfo);
        
            if($getPatientPreviousInfoResult){
                while($row0 = mysqli_fetch_assoc($getPatientPreviousInfoResult)){

                    $checkDeviceAvailability = "SELECT * FROM deviceid WHERE device_id='$device'";
                    $checkDeviceAvailabilityResult = mysqli_query($conn, $checkDeviceAvailability);
                    $checkDeviceAvailabilityCount = mysqli_num_rows($checkDeviceAvailabilityResult);

                    if($checkDeviceAvailabilityCount > 0){
                        while($checkDeviceAvailabilityRow = mysqli_fetch_assoc($checkDeviceAvailabilityResult)){
                            $checkDeviceAvailabilityStatus = "SELECT * FROM deviceid WHERE (device_id='$device' and device_status = 'Available') OR  (device_id='$row0[deviceID]' and device_status = 'Not Available' and device_id='$device')";
                            $checkDeviceAvailabilityStatusResult = mysqli_query($conn, $checkDeviceAvailabilityStatus);
                            $checkDeviceAvailabilityStatusCount = mysqli_num_rows($checkDeviceAvailabilityStatusResult);

                            if($checkDeviceAvailabilityStatusCount > 0){
                                $checkEncoderAvailability = "SELECT * FROM user WHERE user_id='$rowEncoder[user_id]' and isExist = 1";
                                $checkEncoderAvailabilityResult = mysqli_query($conn, $checkEncoderAvailability);
                                $checkEncoderAvailabilityCount = mysqli_num_rows($checkEncoderAvailabilityResult);

                                if($checkEncoderAvailabilityCount > 0){

                                    $updateDevice = "UPDATE deviceid SET device_status = 'Available' WHERE device_id='$row0[deviceID]'";
                                    $updateDeviceResult = $conn->query($updateDevice);
                                
                                    if($updateDeviceResult){
                                        $birthdate = new DateTime($birthdate);
                                        $birthdateFinal = $birthdate->format('F d, Y');

                                        if($row0['encoder'] == $rowEncoder['user_id']){
                                            $sql = "UPDATE patient SET patient_name='$name', gender='$gender', birthdate='$birthdateFinal', address='$address', disease = '$disease', number='$number', room='$room', deviceID='$device', encoder='$rowEncoder[user_id]' WHERE patient_id='$patientID' and isExist=1";
                                            $result = $conn->query($sql);
                                        }
                    
                                        else{
                                            $sql = "UPDATE patient SET patient_name='$name', gender='$gender', birthdate='$birthdateFinal', address='$address', disease = '$disease', number='$number', room='$room', deviceID='$device', encoder='$rowEncoder[user_id]', share_id = '' WHERE patient_id='$patientID' and isExist=1";
                                            $result = $conn->query($sql);
                                        }

                                        if($result){
                                            $updateDevice1 = "UPDATE deviceid SET device_status='Not Available' WHERE device_id='$device'";
                                            $updateDeviceResult1 = mysqli_query($conn, $updateDevice1);

                                            if($updateDeviceResult1){
                                                $sql1 = "SELECT * FROM patient WHERE patient_id = '$patientID' and isExist=1 ORDER BY patient_name ASC";
                                                $result1 = $conn->query($sql1);
    
                                                if($result1){
                                                    while($row = mysqli_fetch_assoc($result1)){
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
                                                            $getStaffName = "SELECT * FROM user WHERE user_id = '$row[encoder]'";
                                                            $getStaffNameResult = mysqli_query($conn, $getStaffName);
    
                                                            if($getStaffNameResult){
                                                                while($row4 = mysqli_fetch_assoc($getStaffNameResult)){
                                                                    echo $row['patient_id']."`".$row['patient_name']."`".$row['gender']."`".$row['birthdate']."`".$row['address']."`".$row['number']."`".$row['disease']."`".$row['deviceID']."`".$row['room']."`".$row4['staff_name'].'`'.$row['date_admit'].'`'.$row['date_dismiss'].'`';
                                                                }
                                                            }
    
                                                            else{
                                                                echo 'failure';
                                                            }
    
                                                        }
                                            
                                                        else{
                                                            echo 'failure';
                                                        }
                                                    }
                                                }
    
                                                else{
                                                    echo 'failure';
                                                }
                                            }

                                            else{

                                            }
                                            
                                        }
                                    
                                        else{
                                            echo 'failure';
                                        }
                                    }
                                
                                    else{
                                        echo 'failure';
                                    }
                                    
                                }

                                else if($checkEncoderAvailabilityCount == 0){
                                    echo 'encoder not exist';
                                }

                                else{
                                    echo 'failure';
                                }                                
                            }

                            else if($checkDeviceAvailabilityStatusCount == 0){
                                echo 'device taken';
                            }
                           
                            else{
                                echo 'failure';
                            }
                        }
                    }

                    else if($checkDeviceAvailabilityCount == 0){
                        echo 'device not exist';
                    }
                   
                    else{
                        echo 'failure';
                    }
                    
                }
            }
        
            else{
                echo 'failure';
            }
        }
    }

    else{
        echo 'failure';
    }

   
}
?>