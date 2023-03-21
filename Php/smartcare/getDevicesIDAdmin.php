<?php
    if(isset($_POST['patient_id'])){

    require_once "conn.php";
    require_once "validate.php";
    
    $patient_id = validate($_POST['patient_id']);


    $sql = "SELECT * FROM patient WHERE patient_id='$patient_id'";
            $result = mysqli_query($conn, $sql);

            if($result){
                while($row=mysqli_fetch_assoc($result)){
                    $sql1 = "SELECT * FROM deviceid WHERE device_id='$row[deviceID]'";
                    $result1 = mysqli_query($conn, $sql1);

                    if($result1){
                        while($row2=mysqli_fetch_assoc($result1)){
                            $sql2= "SELECT * FROM deviceid WHERE device_status = 'Available' or device_id = '$row[deviceID]' ORDER BY device_id ASC";
                            $result2 = mysqli_query($conn, $sql2);

                            if($result2){
                                while($row3=mysqli_fetch_assoc($result2)){
                                    echo $row3['device_id'].'`';
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
            }
        
            else{
                echo 'failure';
            }

}
    
?>