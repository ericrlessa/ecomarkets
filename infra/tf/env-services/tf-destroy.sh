#!/bin/bash
set -x 

export TF_VAR_env_id=$(terraform -chdir=../env-security output -raw env_id)
export TF_VAR_aws_region=$(terraform -chdir=../env-security output -raw aws_region)
export TF_VAR_domain_name=$(terraform -chdir=../env-security output -raw domain_name)

export TF_VAR_db_app_username=$(terraform -chdir=../env-security output -raw db_app_username)
export TF_VAR_db_app_password=$(terraform -chdir=../env-security output -raw db_app_password)

export TF_VAR_twilio_account_sid=$(terraform -chdir=../env-security output -raw twilio_account_sid)
export TF_VAR_twilio_auth_token=$(terraform -chdir=../env-security output -raw twilio_auth_token)
export TF_VAR_twilio_phone_from=$(terraform -chdir=../env-security output -raw twilio_phone_from)

export TF_VAR_oidc_client_id=$(terraform -chdir=../env-security output -raw oidc_client_id)
export TF_VAR_oidc_client_secret=$(terraform -chdir=../env-security output -raw oidc_client_secret)
export TF_VAR_oidc_provider=$(terraform -chdir=../env-security output -raw oidc_provider)

export TF_VAR_vpc_id=$(terraform -chdir=../env-base output -raw vpc_id)
export TF_VAR_ecs_subnets=$(terraform -chdir=../env-base output -raw public_subnet_ids_str)
export TF_VAR_api_subnet_ids=$(terraform -chdir=../env-base output -raw public_subnet_ids_str)

export TF_VAR_db_endpoint=$(terraform -chdir=../env-base output -raw db_endpoint)
export TF_VAR_db_name=$(terraform -chdir=../env-base output -raw db_name)

export TF_VAR_infra_bucket_name=$(terraform -chdir=../env-base output -raw infra_bucket_name)
export TF_VAR_cluster_id=$(terraform -chdir=../env-base output -raw cluster_id)
export TF_VAR_task_execution_role=$(terraform -chdir=../env-base output -raw task_execution_role)
export TF_VAR_listener_arn=$(terraform -chdir=../env-base output -raw listener_arn)


terraform destroy -auto-approve 
