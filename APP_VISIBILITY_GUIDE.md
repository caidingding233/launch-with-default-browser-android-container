# 应用隐藏功能使用指南

## 功能说明

这个应用已经实现了从启动器中隐藏的功能，同时保留了通过系统设置打开的能力。

## 实现方案

### 1. 使用 Activity-Alias
- 在 `AndroidManifest.xml` 中使用了 `<activity-alias>` 标签
- 这个别名包含了 `LAUNCHER` category，可以动态启用/禁用
- MainActivity 本身不再包含 `LAUNCHER` category

### 2. 动态控制
- 通过 `PackageManager.setComponentEnabledSetting()` 动态控制别名的启用状态
- 应用内提供了开关来控制图标的显示/隐藏

### 3. 从设置中打开
即使应用图标被隐藏，用户仍然可以通过以下方式打开应用：
- 设置 → 应用管理 → 找到本应用 → 点击"打开"按钮
- 通过其他应用发送特定的 Intent

## 使用方法

### 隐藏应用图标
1. 打开应用
2. 在主界面找到"应用图标可见性"开关
3. 关闭开关，应用图标将从启动器中消失

### 重新显示应用图标
1. 通过设置 → 应用管理打开应用
2. 打开"应用图标可见性"开关
3. 应用图标将重新出现在启动器中

## 注意事项

1. **首次安装**：应用首次安装后默认是可见的
2. **系统限制**：某些定制ROM可能会限制这个功能
3. **更新影响**：应用更新可能会重置图标的可见性状态
4. **卸载重装**：卸载后重新安装会恢复默认的可见状态

## 技术细节

### 关键代码位置
- `AndroidManifest.xml` - Activity-Alias 配置
- `AppVisibilityHelper.kt` - 可见性控制逻辑
- `MainActivity.kt` - UI控制界面

### Intent Action
应用注册了自定义的 Intent Action：
```
com.caidingding233.myapplication.OPEN_MAIN
```
可以通过这个 Action 从其他应用启动本应用。