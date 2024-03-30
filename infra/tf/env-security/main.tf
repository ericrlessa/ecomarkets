terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  backend "s3" {}
}

provider "aws" {
  region = var.aws_region
}

module "security" {
    source = "../modules/security"
    env_id = var.env_id
    twilio_account_sid = var.twilio_account_sid
    twilio_auth_token = var.twilio_auth_token
    twilio_phone_from = var.twilio_phone_from
}
