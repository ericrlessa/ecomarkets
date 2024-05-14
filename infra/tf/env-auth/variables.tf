variable env_id {}

variable aws_region {
  default = "us-east-1"
}

variable "oidc_google_id_text" {
  description = "The Client ID from Google"
  type        = string
}

variable "oidc_google_secret_text" {
  description = "The Client secrete from Google"
  type        = string
}

