package com.church.appChurch.service;

import com.church.appChurch.model.Evento;
import com.church.appChurch.model.Inscricao;
import com.church.appChurch.repository.EventoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RelatorioServiceImpl implements IRelatorioService{

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public byte[] gerarPlanilhaInscritos(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId.intValue())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        List<Inscricao> inscricoes = evento.getInscricoes();

        // MUDANÇA 1: Usamos SXSSFWorkbook em vez de XSSFWorkbook
        // O parâmetro 100 significa: "Mantenha apenas 100 linhas na RAM, o resto joga pro disco"
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // Comprimir arquivos temporários para economizar espaço em disco (Opcional, mas bom)
            workbook.setCompressTempFiles(true);

            Sheet sheet = workbook.createSheet("Inscritos");

            // O trackAllColumnsForAutoSizing é necessário no SXSSF se você quiser usar autoSizeColumn
            ((org.apache.poi.xssf.streaming.SXSSFSheet) sheet).trackAllColumnsForAutoSizing();

            // --- ESTILOS (Mantém igual) ---
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN); // etc...

            // --- CABEÇALHO ---
            Row headerRow = sheet.createRow(0);
            String[] colunas = {"ID", "Nome Completo", "Email", "Telefone", "Data Inscrição", "Status", "Valor Pago (R$)"};

            for (int i = 0; i < colunas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(colunas[i]);
                cell.setCellStyle(headerStyle);
            }

            // --- PREENCHIMENTO DOS DADOS ---
            int rowIdx = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (Inscricao inscricao : inscricoes) {
                Row row = sheet.createRow(rowIdx++);

                // Dica de Performance: Crie as células diretamente sem estilos complexos se for MUITA gente,
                // mas para < 10k linhas, usar estilo é tranquilo.
                createCell(row, 0, inscricao.getNumeroInscricao(), dataStyle);
                createCell(row, 1, inscricao.getNome(), dataStyle);
                createCell(row, 2, inscricao.getEmail(), dataStyle);
                createCell(row, 3, inscricao.getTelefone(), dataStyle);

                String dataFmt = inscricao.getDataInscricao() != null ? inscricao.getDataInscricao().format(formatter) : "-";
                createCell(row, 4, dataFmt, dataStyle);

                createCell(row, 5, inscricao.getStatus(), dataStyle);

                BigDecimal valor = "PAGO".equalsIgnoreCase(inscricao.getStatus()) ? inscricao.getValorPago() : BigDecimal.valueOf(0);
                createCell(row, 6, valor != null ? valor.toString() : "0.00", dataStyle);
            }

            // AutoSize (Cuidado: isso pode ser lento em SXSSF se tiver milhões de linhas)
            for (int i = 0; i < colunas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);

            // MUDANÇA 2: Limpar os arquivos temporários criados no disco
            workbook.dispose();

            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Falha ao gerar arquivo Excel: " + e.getMessage());
        }
    }

    // Método auxiliar para criar células com estilo
    private void createCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
