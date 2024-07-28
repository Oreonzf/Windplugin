# Minecraft Plugins - Windplugin

Bem-vindo ao repositório dos plugins Home e Windplugin para Minecraft! Estes plugins permitem a personalização e funcionalidades adicionais para o servidor Minecraft, tornando a jogabilidade mais dinâmica e interativa.

## Índice

- [Sobre](#sobre)
- [Funcionalidades](#funcionalidades)
- [Configuração](#configuração)
- [Plugin Windplugin](#plugin-Windplugin)
- [Estrutura do Projeto](#estrutura-do-projeto)

## Sobre

Este repositório contém um plugin de Minecraft relacionado ao Item "Wind Charge" chamado de Windplugin. ele é altamente configurável através do arquivo `config.yml` gerado após a inicialização do servidor.
Por questões de depuração, existe um debbug para análise de como está o funcionamento do plugin.

## Funcionalidades

O Plugin Windplugin é utilizado para configurar o projétil Wind Charge, garantindo um controle do tamanho da explosão gerada pelo projétil, velocidade de deslocamento do projétil e também adiciona partículas 

### Plugin Windplugin

- **Configuração da Explosão**: Poder da explosão configurável.
- **Velocidade do Projétil**: Velocidade do projétil ajustável.
- **Partículas de Explosão**: Ativação de partículas visuais na explosão.

## Configuração

O plugins pode ser configurado pelo arquivo `config.yml` gerado após a inicialização do servidor. Abaixo estão os detalhes das opções de configuração para o plugin.

### Plugin Windplugin
```yaml
# config.yml

explosion-power: 8.0          # Poder da explosão, definido como 8.0 por padrão
projectile-speed: 1.0         # Velocidade do projétil, definida como 1.0 por padrão
enable-particles: true        # Ativa/desativa partículas visuais na explosão
```

### Lógica de Implementação do Windplugin

O `WindChargeListener` é responsável por adicionar funcionalidades ao item Wind Charge no Minecraft, permitindo que ele seja usado como um projétil explosivo com várias configurações personalizáveis. Abaixo está uma visão geral da lógica de implementação:

### WindChargeListener.java

#### Atributos Principais

- `plugin`: Referência à instância do plugin principal (`JavaPlugin`), permitindo acesso à configuração.
- `explosionPower`, `projectileSpeed`, `particleScale`: Configurações personalizáveis para o poder da explosão, velocidade do projétil e escala das partículas.
- `enableParticles`: Booleano que controla se as partículas de explosão são habilitadas.

#### Exemplo:

```java
  public void reloadConfig() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        this.explosionPower = config.getDouble("explosion-power", 8.0);
        this.projectileSpeed = config.getDouble("projectile-speed", 4.0);
        this.particleScale = config.getDouble("particle-scale", 1.0); // Default scale is 1.0
        plugin.getLogger().info("Config reloaded: Explosion power set to: " + this.explosionPower);
        plugin.getLogger().info("Config reloaded: Projectile speed set to: " + this.projectileSpeed);
        plugin.getLogger().info("Config reloaded: Particle scale set to: " + this.particleScale);
    }
```
