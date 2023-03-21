<?php
if(isset($_POST['sharedPatiendID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $sharedPatiendID = validate($_POST['sharedPatiendID']);

    $sql = "SELECT * FROM patient WHERE share_id='$sharedPatiendID' and isExist=1";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){

            if($row['share_id'] == 0){
                echo $row['patient_name']."`".$row['patient_id']."`".$row['room']."`".$row['share_id']."`"."noname"."%";
            }

            else{
                $sql1 = "SELECT * FROM user WHERE user_id='$row[encoder]' and isExist=1";
                $result1 = $conn->query($sql1);
    
                if($result1){
                    while($row1 = mysqli_fetch_assoc($result1)){
                        echo $row['patient_name']."`".$row['patient_id']."`".$row['room']."`".$row['share_id']."`".$row1['staff_name']."%";
                    }
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