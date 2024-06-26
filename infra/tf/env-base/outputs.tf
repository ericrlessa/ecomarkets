
output vpc_id {
    value = module.network.vpc_id
}

output public_subnet_ids {
    value = module.network.public_subnet_ids
}

output public_subnet_ids_str {
    value =  join(",",module.network.public_subnet_ids)
}

output private_subnet_ids {
    value = module.network.private_subnet_ids
}

output private_subnet_ids_str {
    value =  join(",", module.network.private_subnet_ids)
}

output "infra_bucket_name" {
    value = module.infra_storage.bucket_name
}

output "db_endpoint" {
    value = module.database.db_endpoint
}

output "db_port" {
    value = module.database.db_port
}

output "db_name" {
    value = module.database.db_name
}

output "db_master_user_secret" {
    value = module.database.db_master_user_secret
}

output "db_master_user_kms" {
    value = module.database.db_master_user_kms
}

output "cluster_id" {
  value = module.ecs-cluster.cluster_id
}

output "task_execution_role" {
  value = module.ecs-cluster.task_execution_role
}

output alb_dns_name {
    value = "${module.ecs-cluster.alb_dns_name}"
}

output ecs_endpoint {
    value = "https://${var.env_id}.${var.domain_name}"
}

output "listener_arn" {
  value = module.ecs-cluster.listener_arn
}