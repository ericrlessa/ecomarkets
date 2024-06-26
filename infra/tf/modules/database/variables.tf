variable "vpc_id" {}

variable "allow_cidrs" {
    type = list(string)
    default = ["0.0.0.0/0"]
}

variable "db_subnet_ids" {
  type = list(string)
}

variable "db_instance_class" {
  type = string
  default = "db.t4g.medium"
}

variable "db_engine" {
    type = string
    default = "aurora-postgresql"
}

variable "db_engine_version" {
    type = string
    default = "16.1"
}

variable "db_database_name" {
    type = string
    default = "sqldb"
}


variable "db_cluster_identifier" {
    type = string
    default = "dbcluster"
}

variable "publicly_accessible" {
    type = bool
    default = false
}


