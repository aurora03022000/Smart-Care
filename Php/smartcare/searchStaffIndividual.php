<?php
if(isset($_POST['patientID']) && isset($_POST['currentUser']) && isset($_POST['searchName'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patientID = validate($_POST['patientID'] );
    $currentUser = validate($_POST['currentUser'] );
    $searchName = validate($_POST['searchName'] );

    $sql = "SELECT * FROM user WHERE staff_name = '$searchName' and username != 'admin' and isExist=1";
    $result = $conn->query($sql);
    $rowsNumber = mysqli_num_rows($result);

    if($rowsNumber > 0){
        while($row = mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM patient WHERE patient_id = '$patientID' and isExist=1";
            $result1 = $conn->query($sql1);

            if($result1){
                while($row1 = mysqli_fetch_assoc($result1)){
                    if($row['user_id'] == $row1['share_id']){
                        echo 'empty';
                    }

                    else{
                        echo $row['staff_name']."`".$row['user_id']."%";
                    }
                }
            }

            else{
                echo 'failure';
            }

        }
    }

    else if($rowsNumber == 0){
        if($searchName == ""){
            $sql2 = "SELECT * FROM patient WHERE patient_id = '$patientID' and isExist=1";
            $result2 = $conn->query($sql2);

            if($result2){
                while($row2 = mysqli_fetch_assoc($result2)){
                    $sql3 = "SELECT * FROM user WHERE user_id != '$row2[share_id]' and username != 'admin' and user_id != '$currentUser' and isExist=1";
                    $result3 = $conn->query($sql3);

                    if($result3){
                        while($row3 = mysqli_fetch_assoc($result3)){
                            echo $row3['staff_name']."`".$row3['user_id']."%";
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
            echo 'empty'; 
        }
    }

    else{
        echo 'failure';
    }
    

}
?>