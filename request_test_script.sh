#!/bin/bash

base_url="http://localhost:8080"
num_requests=10000
existing_ids=()

# Varolan öğrenci kimliklerini al
existing_students=$(curl -s "${base_url}/getAll")
if [[ $? -eq 0 ]]; then
    existing_ids=($(echo "$existing_students" | jq -r '.[].id'))
fi

# Endpoint değerlerini eşit sayıda tekrarla
endpoints=("save" "get" "getAll" "delete" "error")
num_endpoints=${#endpoints[@]}

# Rastgele istekleri gönder
for ((i=1; i<=$num_requests; i++)); do
    endpoint_index=$((i % num_endpoints))
    endpoint=${endpoints[$endpoint_index]}
    student_id=""

    if [[ ${#existing_ids[@]} -gt 0 && $((RANDOM % 2)) -eq 0 ]]; then
        # Mevcut bir student_id kullan
        student_id=${existing_ids[$((RANDOM % ${#existing_ids[@]}))]}
    else {
        # Mevcut olmayan bir student_id kullan
        student_id=$((10000 + RANDOM))
    }
    fi

    case $endpoint in
        "save")
            # save endpoint
            data='{
                "schoolNumber": '$((1000 + RANDOM % 9000))',
                "name": "RandomName",
                "surName": "RandomSurname",
                "schoolName": "RandomSchool"
            }'
            response=$(curl -s -X POST -H "Content-Type: application/json" -d "$data" "${base_url}/save")
            if [[ $? -eq 0 ]]; then
                echo "Save isteği başarılı: $response"
            else
                echo "Save isteği hatası: $response"
            fi
            ;;
        "get")
            # get endpoint
            response=$(curl -s "${base_url}/get?id=${student_id}")
            if [[ $? -eq 0 ]]; then
                echo "Get isteği başarılı: $response"
            else {
                echo "Get isteği hatası: $response"
            }
            fi
            ;;
        "getAll")
            # getAll endpoint
            response=$(curl -s "${base_url}/getAll")
            if [[ $? -eq 0 ]]; then
                echo "GetAll isteği başarılı: $response"
            else
                echo "GetAll isteği hatası: $response"
            fi
            ;;
        "delete")
            # delete endpoint
            response=$(curl -s -X POST -H "Content-Type: application/json" -d '{"id": '$student_id'}' "${base_url}/delete")
            if [[ $? -eq 0 ]]; then
                echo "Delete isteği başarılı. Öğrenci ID: $student_id"
            else
                echo "Delete isteği hatası: $response"
            fi
            ;;
        "error")
            # Simulate 400 error
            response=$(curl -s -X GET "${base_url}/nonexistent_endpoint")
            if [[ $? -ne 0 || $(echo "$response" | grep -c "HTTP Status 404") -gt 0 ]]; then
                echo "404 hatası alındı: $response"
            else
                echo "404 hatası alınmadı: $response"
            fi
            ;;
    esac
done
