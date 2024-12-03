import { test, expect } from '@playwright/test';

test('CompSampleSD_EmptyInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByRole('button', { name: 'Compute Sample Standard' }).click();
  await page.getByText('Invalid Input: The \'values\'').click();
});


test('CompPopulationSD_EmptyInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByRole('button', { name: 'Compute Population Standard' }).click();
  await page.getByText('Invalid Input: The \'values\'').click();
});


test('CompMean_EmptyInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByRole('button', { name: 'Compute Mean | one value per' }).click();
  await page.getByText('Invalid Input: The \'values\'').click();
});


test('CompZScore_EmptyInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByRole('button', { name: 'Compute Z Score | value, mean' }).click();
  await page.getByText('Invalid Input: The \'values\'').click();
});


test('CompLinearRegression_EmptyInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByRole('button', { name: 'Compute Linear Regression |' }).click();
  await page.getByText('Invalid Input: The \'values\'').click();
});


test('PredictY_EmptyInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByRole('button', { name: 'Predict Y | x,m,b on one line' }).click();
  await page.getByText('Invalid Input: The \'values\'').click();
});


test('CompSampleSD_InvalidInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('6 7 8');
  await page.getByRole('button', { name: 'Compute Sample Standard' }).click();
  await page.getByText('Invalid Input: Values must be').click();
});


test('CompPopulationSD_InvalidInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('1234 5678');
  await page.getByRole('button', { name: 'Compute Population Standard' }).click();
  await page.getByText('Invalid Input: Values must be').click();
});


test('CompMean_InvalidInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('1234 5678');
  await page.getByRole('button', { name: 'Compute Mean | one value per' }).click();
  await page.getByText('Invalid Input: Values must be').click();
});


test('CompZScore_InvalidInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('6,7,\n8');
  await page.getByRole('button', { name: 'Compute Z Score | value, mean' }).click();
  await page.getByText('Invalid Input: Input must be').click();
});


test('CompLinearRegression_InvalidInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('5,6\n67');
  await page.getByRole('button', { name: 'Compute Linear Regression |' }).click();
  await page.getByText('Invalid Input: Each line must').click();
});

test('PredictY_InvalidInput_Sad', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('6,7\n8');
  await page.getByRole('button', { name: 'Predict Y | x,m,b on one line' }).click();
  await page.getByText('Invalid Input: Input must be').click();
});



