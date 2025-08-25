# 🚀 CI/CD Pipeline - MortApp

Este proyecto incluye un pipeline de CI/CD automatizado usando GitHub Actions que se ejecuta en cada
push y pull request.

## 📋 Workflows Configurados

### 1. **CI (Continuous Integration)**

**Archivo:** `.github/workflows/ci.yml`

Se ejecuta en:

- Push a ramas `main` y `develop`
- Pull requests a ramas `main` y `develop`

**Verificaciones:**

- ✅ **KtLint Check** - Verifica formato de código
- ⚠️ **Detekt** - Análisis estático de código (no bloquea el build)
- 🧪 **Unit Tests** - Ejecuta todas las pruebas unitarias

### 2. **Build Verification**

**Archivo:** `.github/workflows/build.yml`

Se ejecuta en:

- Push a ramas `main` y `develop`
- Pull requests a ramas `main` y `develop`

**Verificaciones:**

- 🏗️ **Build Debug APK** - Compila el proyecto
- 📦 **Upload APK** - Sube el APK como artefacto (solo en PRs)

### 3. **Quality Gate**

**Archivo:** `.github/workflows/quality-gate.yml`

Se ejecuta en:

- Pull requests a rama `main`

**Verificaciones:**

- 🧹 **Clean Build** - Compilación limpia
- 📊 **Code Quality** - Ejecuta `./gradlew codeQuality`
- 🧪 **All Tests** - Ejecuta todas las pruebas
- 📈 **Quality Report** - Genera reporte detallado
- 💬 **PR Comment** - Comenta resultados en el PR

## 🛠️ Comandos de Calidad Local

Antes de hacer push, puedes ejecutar estos comandos localmente:

```bash
# Verificar formato de código
./gradlew ktlintCheck

# Formatear código automáticamente
./gradlew ktlintFormat

# Análisis estático con Detekt
./gradlew detekt

# Ejecutar todas las verificaciones
./gradlew codeQuality

# Ejecutar tests
./gradlew test

# Build completo
./gradlew build
```

## 📊 Reportes y Artefactos

Los workflows generan varios tipos de reportes:

### KtLint Reports

- **Formato:** Plain text, Checkstyle XML, SARIF
- **Ubicación:** `**/build/reports/ktlint/`

### Detekt Reports

- **Formato:** HTML, XML, TXT
- **Ubicación:** `**/build/reports/detekt/`

### Test Reports

- **Formato:** HTML, XML, JUnit
- **Ubicación:** `**/build/reports/tests/`

### Artefactos Disponibles

- 📁 `ktlint-reports` (30 días)
- 📁 `detekt-reports` (30 días)
- 📁 `test-reports` (30 días)
- 📁 `debug-apk` (7 días, solo PRs)
- 📁 `quality-report` (30 días)
- 📁 `all-quality-reports` (30 días)

## 🚦 Estados del Pipeline

### ✅ Success

- Todos los checks de KtLint pasan
- Build es exitoso
- Tests unitarios pasan

### ⚠️ Warning

- Detekt encuentra issues (no bloquea)
- Algunos tests fallan pero build continúa

### ❌ Failure

- KtLint encuentra violaciones de formato
- Build falla
- Tests críticos fallan

## 🔧 Configuración

### Modificar Comportamiento

**Para cambiar ramas monitoreadas:**

```yaml
on:
  push:
    branches: [ main, develop, tu-rama ]
```

**Para hacer Detekt bloqueante:**

```yaml
- name: Run Detekt
  run: ./gradlew detekt
  # Remover: continue-on-error: true
```

**Para agregar más checks:**

```yaml
- name: Run Custom Check
  run: ./gradlew tuCustomTask
```

## 📱 Integración con PRs

Cuando crees un PR, automáticamente:

1. 🤖 Se ejecutan todos los workflows
2. 💬 Bot comenta resultados en el PR
3. ✅ Status checks aparecen en GitHub
4. 📁 Reportes se suben como artifacts

## ⚡ Tips de Desarrollo

1. **Antes del commit:**
   ```bash
   ./gradlew ktlintFormat codeQuality test
   ```

2. **Para PRs grandes:**
    - El Quality Gate genera reportes detallados
    - Revisa artifacts si hay fallos

3. **Tests unitarios:**
    - Aunque algunos fallen, el pipeline continúa
    - Revisa reportes para identificar problemas

4. **Formato de código:**
    - KtLint es estricto - usa `ktlintFormat` para auto-fix
    - Configuración en `.editorconfig`

---

*Pipeline configurado para MortApp - Android Clean Architecture* 🎯
