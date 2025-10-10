# Compilador e comandos
JAVAC = javac
JAVA = java

# Diretórios
SRC_DIR = src
BIN_DIR = bin

# Argumentos (podem ser passados ao rodar o make)
MM ?= $(SRC_DIR)/arquivos/MM
PALAVRA ?= $(SRC_DIR)/arquivos/palavra

# Encontra todos os .java dentro de src/
SOURCES = $(shell find $(SRC_DIR) -name "*.java")

# Classe principal (ajuste se o nome for diferente)
MAIN_CLASS = principal.Main

# Regra padrão: compilar e rodar
all: compile run

# Compila todos os .java e coloca os .class em bin/
compile:
	mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) $(SOURCES)

# Executa o programa passando os arquivos informados
run:
	$(JAVA) -cp $(BIN_DIR) $(MAIN_CLASS) $(MM) $(PALAVRA)

# Limpa os arquivos compilados
clean:
	rm -rf $(BIN_DIR)


# Para selecionar MM e palavra especifica:
# make MM=arquivos/MM PALAVRA=arquivos/palavra

# Caso execute apenas 'make'
# Usa os txt padrao