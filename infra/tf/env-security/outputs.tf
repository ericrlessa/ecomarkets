output env_id {
    value = var.env_id
}
output aws_region {
    value = var.aws_region
}

output "db_username" {
    value = module.security.db_username
}

output "db_password" {
    value = module.security.db_password
}

output "db_app_username" {
    value = module.security.db_app_username
}

output "db_app_password" {
    value = module.security.db_app_password
}

output "domain_name" {
    value = var.domain_name
}