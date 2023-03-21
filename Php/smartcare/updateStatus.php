<?php
if(isset($_POST['value']) && isset($_POST['valueToEdit']) && isset($_POST['currentPatientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $value = validate($_POST['value']);
    $valueToEdit = validate($_POST['valueToEdit']);
    $currentPatientID = validate($_POST['currentPatientID']);

    if($valueToEdit == "Pulse Rate"){
        $sql = "UPDATE patient_health_log SET pulse_rate='$value' WHERE patient_id = '$currentPatientID' and isExist=1";
        $result = mysqli_query($conn, $sql);
    
        if($result){
            $sql1 = "SELECT * FROM patient_health_log WHERE patient_id='$currentPatientID' and isExist=1 ORDER BY log_id DESC LIMIT 1";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row = mysqli_fetch_assoc($result1)){
                    $formatted_date = date('F d, Y');
                    date_default_timezone_set("Asia/Manila");
                    $time = date("h:i a");

                    $sql2 = "INSERT INTO patient_logs(patient_id, date, time, pulse_rate, oxygen_saturation, body_temperature, blood_pressure, respiratory_rate, room_temperature, humidity, air_quality) VALUES('$currentPatientID', '$formatted_date', '$time', '$row[pulse_rate]', '$row[oxygen_saturation]', '$row[body_temperature]', '$row[blood_pressure]', '$row[respiratory_rate]', '$row[room_temperature]', '$row[humidity]', '$row[air_quality]')";
                    $result2 = mysqli_query($conn, $sql2);

                    if($result2){
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

    else if($valueToEdit == "Oxygen Saturation"){
        $sql = "UPDATE patient_health_log SET oxygen_saturation='$value' WHERE patient_id = '$currentPatientID'";
        $result = mysqli_query($conn, $sql);
    
        if($result){
            $sql1 = "SELECT * FROM patient_health_log WHERE patient_id='$currentPatientID' ORDER BY log_id DESC LIMIT 1";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row = mysqli_fetch_assoc($result1)){
                    $formatted_date = date('F d, Y');
                    date_default_timezone_set("Asia/Manila");
                    $time = date("h:i a");

                    $sql2 = "INSERT INTO patient_logs(patient_id, date, time, pulse_rate, oxygen_saturation, body_temperature, blood_pressure, respiratory_rate, room_temperature, humidity, air_quality) VALUES('$currentPatientID', '$formatted_date', '$time', '$row[pulse_rate]', '$row[oxygen_saturation]', '$row[body_temperature]', '$row[blood_pressure]', '$row[respiratory_rate]', '$row[room_temperature]', '$row[humidity]', '$row[air_quality]')";
                    $result2 = mysqli_query($conn, $sql2);

                    if($result2){
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

    else if($valueToEdit == "Body Temperature"){
        $sql = "UPDATE patient_health_log SET body_temperature='$value' WHERE patient_id = '$currentPatientID'";
        $result = mysqli_query($conn, $sql);
    
        if($result){
            $sql1 = "SELECT * FROM patient_health_log WHERE patient_id='$currentPatientID' ORDER BY log_id DESC LIMIT 1";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row = mysqli_fetch_assoc($result1)){
                    $formatted_date = date('F d, Y');
                    date_default_timezone_set("Asia/Manila");
                    $time = date("h:i a");

                    $sql2 = "INSERT INTO patient_logs(patient_id, date, time, pulse_rate, oxygen_saturation, body_temperature, blood_pressure, respiratory_rate, room_temperature, humidity, air_quality) VALUES('$currentPatientID', '$formatted_date', '$time', '$row[pulse_rate]', '$row[oxygen_saturation]', '$row[body_temperature]', '$row[blood_pressure]', '$row[respiratory_rate]', '$row[room_temperature]', '$row[humidity]', '$row[air_quality]')";
                    $result2 = mysqli_query($conn, $sql2);

                    if($result2){
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

    else if($valueToEdit == "Blood Pressure"){
        $sql = "UPDATE patient_health_log SET blood_pressure='$value' WHERE patient_id = '$currentPatientID'";
        $result = mysqli_query($conn, $sql);
    
        if($result){
            $sql1 = "SELECT * FROM patient_health_log WHERE patient_id='$currentPatientID' ORDER BY log_id DESC LIMIT 1";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row = mysqli_fetch_assoc($result1)){
                    $formatted_date = date('F d, Y');
                    date_default_timezone_set("Asia/Manila");
                    $time = date("h:i a");

                    $sql2 = "INSERT INTO patient_logs(patient_id, date, time, pulse_rate, oxygen_saturation, body_temperature, blood_pressure, respiratory_rate, room_temperature, humidity, air_quality) VALUES('$currentPatientID', '$formatted_date', '$time', '$row[pulse_rate]', '$row[oxygen_saturation]', '$row[body_temperature]', '$row[blood_pressure]', '$row[respiratory_rate]', '$row[room_temperature]', '$row[humidity]', '$row[air_quality]')";
                    $result2 = mysqli_query($conn, $sql2);

                    if($result2){
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

    else if($valueToEdit == "Respiratory Rate"){
        $sql = "UPDATE patient_health_log SET respiratory_rate='$value' WHERE patient_id = '$currentPatientID'";
        $result = mysqli_query($conn, $sql);
    
        if($result){
            $sql1 = "SELECT * FROM patient_health_log WHERE patient_id='$currentPatientID' ORDER BY log_id DESC LIMIT 1";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row = mysqli_fetch_assoc($result1)){
                    $formatted_date = date('F d, Y');
                    date_default_timezone_set("Asia/Manila");
                    $time = date("h:i a");

                    $sql2 = "INSERT INTO patient_logs(patient_id, date, time, pulse_rate, oxygen_saturation, body_temperature, blood_pressure, respiratory_rate, room_temperature, humidity, air_quality) VALUES('$currentPatientID', '$formatted_date', '$time', '$row[pulse_rate]', '$row[oxygen_saturation]', '$row[body_temperature]', '$row[blood_pressure]', '$row[respiratory_rate]', '$row[room_temperature]', '$row[humidity]', '$row[air_quality]')";
                    $result2 = mysqli_query($conn, $sql2);

                    if($result2){
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

    else if($valueToEdit == "Room Temperature"){
        $sql = "UPDATE patient_health_log SET room_temperature='$value' WHERE patient_id = '$currentPatientID'";
        $result = mysqli_query($conn, $sql);
    
        if($result){
            $sql1 = "SELECT * FROM patient_health_log WHERE patient_id='$currentPatientID' ORDER BY log_id DESC LIMIT 1";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row = mysqli_fetch_assoc($result1)){
                    $formatted_date = date('F d, Y');
                    date_default_timezone_set("Asia/Manila");
                    $time = date("h:i a");

                    $sql2 = "INSERT INTO patient_logs(patient_id, date, time, pulse_rate, oxygen_saturation, body_temperature, blood_pressure, respiratory_rate, room_temperature, humidity, air_quality) VALUES('$currentPatientID', '$formatted_date', '$time', '$row[pulse_rate]', '$row[oxygen_saturation]', '$row[body_temperature]', '$row[blood_pressure]', '$row[respiratory_rate]', '$row[room_temperature]', '$row[humidity]', '$row[air_quality]')";
                    $result2 = mysqli_query($conn, $sql2);

                    if($result2){
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

    else if($valueToEdit == "Humidity"){
        $sql = "UPDATE patient_health_log SET humidity='$value' WHERE patient_id = '$currentPatientID'";
        $result = mysqli_query($conn, $sql);
    
        if($result){
            $sql1 = "SELECT * FROM patient_health_log WHERE patient_id='$currentPatientID' ORDER BY log_id DESC LIMIT 1";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row = mysqli_fetch_assoc($result1)){
                    $formatted_date = date('F d, Y');
                    date_default_timezone_set("Asia/Manila");
                    $time = date("h:i a");

                    $sql2 = "INSERT INTO patient_logs(patient_id, date, time, pulse_rate, oxygen_saturation, body_temperature, blood_pressure, respiratory_rate, room_temperature, humidity, air_quality) VALUES('$currentPatientID', '$formatted_date', '$time', '$row[pulse_rate]', '$row[oxygen_saturation]', '$row[body_temperature]', '$row[blood_pressure]', '$row[respiratory_rate]', '$row[room_temperature]', '$row[humidity]', '$row[air_quality]')";
                    $result2 = mysqli_query($conn, $sql2);

                    if($result2){
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
    

    else if($valueToEdit == "Air Quality"){
        $sql = "UPDATE patient_health_log SET air_quality='$value' WHERE patient_id = '$currentPatientID'";
        $result = mysqli_query($conn, $sql);
    
        if($result){
            $sql1 = "SELECT * FROM patient_health_log WHERE patient_id='$currentPatientID' ORDER BY log_id DESC LIMIT 1";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row = mysqli_fetch_assoc($result1)){
                    $formatted_date = date('F d, Y');
                    date_default_timezone_set("Asia/Manila");
                    $time = date("h:i a");

                    $sql2 = "INSERT INTO patient_logs(patient_id, date, time, pulse_rate, oxygen_saturation, body_temperature, blood_pressure, respiratory_rate, room_temperature, humidity, air_quality) VALUES('$currentPatientID', '$formatted_date', '$time', '$row[pulse_rate]', '$row[oxygen_saturation]', '$row[body_temperature]', '$row[blood_pressure]', '$row[respiratory_rate]', '$row[room_temperature]', '$row[humidity]', '$row[air_quality]')";
                    $result2 = mysqli_query($conn, $sql2);

                    if($result2){
                        echo 'success';
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
            echo 'failure';
        }
    }

    
}
?>