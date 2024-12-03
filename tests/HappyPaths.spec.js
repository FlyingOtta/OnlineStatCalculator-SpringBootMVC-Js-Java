import { test, expect } from '@playwright/test';

test('CompSampleSD_Happy', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('5\n6\n7\n8');
  await page.getByRole('button', { name: 'Compute Sample Standard' }).click();
  await page.getByText('1.2909944487358056').click();
});


test('CompPopulationSD_Happy', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').dblclick();
  await page.getByPlaceholder('Enter one value per line').fill('7\n6\n9\n10\n11');
  await page.getByRole('button', { name: 'Compute Population Standard' }).click();
  await page.getByText('1.8547236990991407').click();
});


test('CompMean_Happy', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('10\n8\n19\n7');
  await page.getByRole('button', { name: 'Compute Mean | one value per' }).click();
  await page.getByText('11.0').click();
});


test('CompZScore_Happy', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('10,5,2');
  await page.getByRole('button', { name: 'Compute Z Score | value, mean' }).click();
  await page.getByText('2.5').click();
});


test('CompLinearRegression_Happy', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('5,3\n9,0\n1,3');
  await page.getByRole('button', { name: 'Compute Linear Regression |' }).click();
  await page.getByText('-').click();
  await page.getByText('3.875').click();
});



test('PredictY_Happy', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('10,2,5');
  await page.getByRole('button', { name: 'Predict Y | x,m,b on one line' }).click();
  await page.getByText('25.0').click();
});


test('CosecutiveCalculationsAllowed_Happy', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('6\n7\n8\n9');
  await page.getByRole('button', { name: 'Compute Mean | one value per' }).click();
  await page.getByText('7.5').click();
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('5\n10\n15\n20');
  await page.getByRole('button', { name: 'Compute Sample Standard' }).click();
  await page.getByText('6.454972243679028').click();
});


test('ClearFunction', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.getByPlaceholder('Enter one value per line').click();
  await page.getByPlaceholder('Enter one value per line').fill('11\n23\n5\n8\n29\n');
  await page.getByRole('button', { name: 'Compute Mean | one value per' }).click();
  await page.getByRole('button', { name: 'Clear' }).click();
});
