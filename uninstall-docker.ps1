# Docker Desktop 完全卸载脚本
# 请以管理员身份运行此脚本

Write-Host "开始卸载 Docker Desktop..." -ForegroundColor Green

# 1. 停止Docker服务
Write-Host "停止 Docker 服务..." -ForegroundColor Yellow
Stop-Service -Name "Docker Desktop Service" -Force -ErrorAction SilentlyContinue
Stop-Process -Name "Docker Desktop" -Force -ErrorAction SilentlyContinue
Stop-Process -Name "dockerd" -Force -ErrorAction SilentlyContinue

# 2. 卸载程序（需要手动确认）
Write-Host "请手动卸载 Docker Desktop:" -ForegroundColor Red
Write-Host "1. Win + R -> appwiz.cpl" -ForegroundColor Yellow
Write-Host "2. 找到 Docker Desktop -> 右键卸载" -ForegroundColor Yellow
Read-Host "卸载完成后按回车继续..."

# 3. 清理用户数据
Write-Host "清理用户数据..." -ForegroundColor Yellow
$paths = @(
    "$env:USERPROFILE\.docker",
    "$env:APPDATA\Docker",
    "$env:APPDATA\Docker Desktop",
    "$env:LOCALAPPDATA\Docker",
    "C:\Program Files\Docker",
    "C:\ProgramData\Docker",
    "C:\ProgramData\DockerDesktop"
)

foreach ($path in $paths) {
    if (Test-Path $path) {
        Write-Host "删除: $path" -ForegroundColor Gray
        Remove-Item -Recurse -Force $path -ErrorAction SilentlyContinue
    }
}

# 4. 清理WSL分布
Write-Host "清理 WSL 分布..." -ForegroundColor Yellow
$wslDistros = wsl --list --quiet
if ($wslDistros -contains "docker-desktop") {
    wsl --unregister docker-desktop
    Write-Host "已删除 docker-desktop WSL 分布" -ForegroundColor Gray
}
if ($wslDistros -contains "docker-desktop-data") {
    wsl --unregister docker-desktop-data
    Write-Host "已删除 docker-desktop-data WSL 分布" -ForegroundColor Gray
}

# 5. 清理注册表
Write-Host "清理注册表..." -ForegroundColor Yellow
$regPaths = @(
    "HKCU:\Software\Docker Inc.",
    "HKLM:\SOFTWARE\Docker Inc."
)

foreach ($regPath in $regPaths) {
    if (Test-Path $regPath) {
        Remove-Item -Path $regPath -Recurse -Force -ErrorAction SilentlyContinue
        Write-Host "已删除注册表项: $regPath" -ForegroundColor Gray
    }
}

Write-Host "Docker Desktop 清理完成！" -ForegroundColor Green
Write-Host "建议重启电脑后再重新安装 Docker Desktop" -ForegroundColor Yellow

# 验证清理结果
Write-Host "`n验证清理结果:" -ForegroundColor Green
try {
    docker --version
    Write-Host "警告: Docker 命令仍然可用，可能需要手动清理 PATH 环境变量" -ForegroundColor Red
} catch {
    Write-Host "✓ Docker 命令已清理" -ForegroundColor Green
}

Read-Host "按回车退出..." 